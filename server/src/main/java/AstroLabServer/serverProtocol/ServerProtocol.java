package AstroLabServer.serverProtocol;

import AstroLab.auth.UserDTO;
import AstroLab.utils.tcpProtocol.ProtocolStates;
import AstroLab.utils.tcpProtocol.TcpProtocol;
import AstroLab.utils.tcpProtocol.packet.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Setter
public class ServerProtocol extends TcpProtocol {
    private static final Logger LOGGER    = LogManager.getLogger(ServerProtocol.class);
    private static final int    BUF_SIZE  = PacketReader.INITIAL_BUFFER_SIZE;

    private final SocketChannel          channel;
    private final PacketReader           reader     = new PacketReader();
    private final ByteBuffer             readBuffer = ByteBuffer.allocate(BUF_SIZE);
    private final ObjectMapper           objectMapper;
    private final Queue<ByteBuffer>      writeQueue = new ArrayDeque<>();
    private UserDTO                user = null;

    public boolean isAuthenticated() {
        return user != null;
    }

    public ServerProtocol(SocketChannel socketChannel) {
        this.channel = socketChannel;
        this.objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Non-blocking "read everything that is availableб try to build a package.
     * If the package is not complete yet, it returns null (we will wait for the next OP_READ).
     * As soon as the full package is available, we deserialize and return it.
     */
    @Override
    public <T> T receive(Class<T> type)
            throws PacketIsNullException, ClientClosedConnectionException, IOException
    {
        int n = channel.read(readBuffer);
        if (n == -1) {
            throw new ClientClosedConnectionException("Client disconnected: " + channel);
        }
        if (n > 0) {
            readBuffer.flip();
            reader.feed(readBuffer);
            readBuffer.compact();
        }

        Packet packet = reader.nextPacket();
        if (packet == null) {
            return null;
        }

        if (packet.getData() == null) {
            throw new PacketIsNullException("Received empty packet from " + channel);
        }
        try {
            return objectMapper.readValue(packet.getData(), type);
        } catch (IOException e) {
            LOGGER.error("Failed to deserialize from {}: {}", channel, e.getMessage());
            throw e;
        }
    }

    /**
     * Serialize the object and put the finished ByteBuffer in the queue.
     * The non-blocking write itself will occur in the selector
     */
    @Override
    public <T> ProtocolStates send(T data) {
        try {
            byte[] payload = objectMapper.writeValueAsBytes(data);
            ByteBuffer pkt = Packet.encode(payload);
            writeQueue.add(pkt);
            return ProtocolStates.SEND_SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Failed to enqueue send: {}", e.getMessage());
            return ProtocolStates.SEND_FAILED;
        }
    }

    public void flushWrites(SelectionKey key) throws IOException {
        while (!writeQueue.isEmpty()) {
            ByteBuffer buf = writeQueue.peek();
            channel.write(buf);
            if (buf.hasRemaining()) {
                return;
            }
            writeQueue.poll();
        }
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        key.selector().wakeup();
    }
}