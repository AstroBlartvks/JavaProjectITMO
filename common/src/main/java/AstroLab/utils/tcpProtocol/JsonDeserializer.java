package AstroLab.utils.tcpProtocol;

import java.io.IOException;

public interface JsonDeserializer { <T> T deserializeFromBytes(byte[] bytes,
                                                               Class<T> type) throws IOException;
}
