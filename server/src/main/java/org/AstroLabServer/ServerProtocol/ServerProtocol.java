package org.AstroLabServer.ServerProtocol;

import org.AstroLab.utils.tcpProtocol.ProtocolStates;
import org.AstroLab.utils.tcpProtocol.TcpProtocol;
import org.AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import org.AstroLab.utils.tcpProtocol.packet.Packet;
import org.AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import org.AstroLab.utils.tcpProtocol.packet.PacketReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerProtocol extends TcpProtocol {
    private static final Logger logger = LogManager.getLogger(ServerProtocol.class);
    private final SocketChannel socketChannel;

    public ServerProtocol(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    @Override
    public <T> T receive(Class<T> type) throws PacketIsNullException, SocketTimeoutException {
        PacketReader reader = new PacketReader();
        ByteBuffer readBuffer = ByteBuffer.allocate(PacketReader.INITIAL_BUFFER_SIZE);
        try {
            while (reader.notFullPacket()) {
                readBuffer.clear();
                int bytesRead = socketChannel.read(readBuffer);
                if (bytesRead == -1) {
                    throw new ClientClosedConnectionException("Client = "+socketChannel+" disconnected");
                }
                readBuffer.flip();
                reader.feed(readBuffer);
            }
            Packet packet = reader.nextPacket();
            return deserializeFromBytes(packet.getData(), type);
        } catch (SocketTimeoutException e){
            throw e;
        } catch (IOException e){
            logger.error("Packet from {} is null!", socketChannel);
        }
        throw new PacketIsNullException("Packet from "+socketChannel+" is null!");
    }

    @Override
    public <T> ProtocolStates send(T data) {
        try {
            ByteBuffer packet = Packet.encode(serializeToBytes(data));
            while (packet.hasRemaining()) {
                socketChannel.write(packet);
            }
            logger.info("The request was successfully sent to the client={}", socketChannel);
        } catch (Exception e) {
            logger.error("Error while server sending {}", e.getMessage());
            return ProtocolStates.SEND_FAILED;
        }
        return ProtocolStates.SEND_SUCCESS;
    }
}
