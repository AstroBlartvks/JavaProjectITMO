package AstroLabClient.ClientProtocol;

import AstroLab.utils.tcpProtocol.ProtocolStates;
import AstroLab.utils.tcpProtocol.TcpProtocol;
import AstroLab.utils.tcpProtocol.packet.Packet;
import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import AstroLab.utils.tcpProtocol.packet.PacketReader;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class ClientProtocol extends TcpProtocol {
    private final Socket socket;

    public ClientProtocol(Socket socket) {
        this.socket = socket;
    }

    @Override
    public <T> T receive(Class<T> type) throws PacketIsNullException, SocketTimeoutException {
        PacketReader reader = new PacketReader();
        ByteBuffer readBuffer = ByteBuffer.allocate(PacketReader.INITIAL_BUFFER_SIZE);

        try {
            InputStream in = socket.getInputStream();
            while (reader.notFullPacket()) {
                int status = in.read(readBuffer.array(), 0, readBuffer.capacity());
                if (status == -1) {
                    throw new IOException("Server closed!");
                }
                readBuffer.position(0);
                readBuffer.limit(status);
                reader.feed(readBuffer);
            }
            Packet packet = reader.nextPacket();
            return deserializeFromBytes(packet.getData(), type);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            System.err.println("Receiving failed: " + e);
            return null;
        }
    }

    @Override
    public <T> ProtocolStates send(T data) {
        try {
            ByteBuffer packet = Packet.encode(serializeToBytes(data));
            OutputStream out = socket.getOutputStream();
            out.write(packet.array(), 0, packet.limit());
            out.flush();
            return ProtocolStates.SEND_SUCCESS;
        } catch (IOException e) {
            System.err.println("Sending failed: " + e.getMessage());
            return ProtocolStates.SEND_FAILED;
        }
    }
}
