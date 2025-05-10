package AstroLab.utils.tcpProtocol;

public interface Sender {
    <T> ProtocolStates send(T buffer);
}
