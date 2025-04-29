package org.AstroLabClient.ClientProtocol;

import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.tcpProtocol.ProtocolStates;
import org.AstroLab.utils.tcpProtocol.TcpProtocol;
import org.AstroLab.utils.tcpProtocol.packet.Packet;
import org.AstroLab.utils.tcpProtocol.packet.PacketReader;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ClientProtocol extends TcpProtocol {
    Socket socket;

    public ClientProtocol(Socket socket){
        this.socket = socket;
    }

    @Override
    public Packet receive() {
        PacketReader reader = new PacketReader();
        ByteBuffer readBuffer = ByteBuffer.allocate(PacketReader.INITIAL_BUFFER_SIZE);

        try {
            InputStream in = socket.getInputStream();
            while (!reader.hasPacket()) {
                int status = in.read(readBuffer.array(), 0, readBuffer.capacity());
                if (status == -1) {
                    throw new IOException("Server closed!");
                }
                readBuffer.position(0);
                readBuffer.limit(status);
                reader.feed(readBuffer);
            }
            return reader.nextPacket();
        } catch (IOException e) {
            System.err.println("Receiving failed: " + e.getMessage());
            return null;
        }
    }


    @Override
    public <T> ProtocolStates send(T data) {
        try {
            ByteBuffer packet = Packet.encode(serializeToBytes(data));;
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
