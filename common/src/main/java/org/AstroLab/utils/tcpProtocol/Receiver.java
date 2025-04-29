package org.AstroLab.utils.tcpProtocol;

import org.AstroLab.utils.tcpProtocol.packet.Packet;
import org.AstroLab.utils.tcpProtocol.packet.PacketIsNullException;

public interface Receiver {
    Packet receive() throws PacketIsNullException;
}
