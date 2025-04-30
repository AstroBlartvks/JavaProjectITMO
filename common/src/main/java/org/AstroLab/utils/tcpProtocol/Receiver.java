package org.AstroLab.utils.tcpProtocol;

import org.AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import java.net.SocketTimeoutException;

public interface Receiver {
    <T> T receive(Class<T> type) throws PacketIsNullException, SocketTimeoutException;
}
