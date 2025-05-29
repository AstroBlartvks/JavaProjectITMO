package AstroLab.utils.tcpProtocol;

import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Receiver {
    <T> T receive(Class<T> type) throws PacketIsNullException, SocketTimeoutException, IOException;
}
