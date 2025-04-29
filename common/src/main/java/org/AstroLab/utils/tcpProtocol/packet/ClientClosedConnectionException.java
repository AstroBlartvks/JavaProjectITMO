package org.AstroLab.utils.tcpProtocol.packet;

public class ClientClosedConnectionException extends RuntimeException {
    public ClientClosedConnectionException(String message) {
        super(message);
    }
}
