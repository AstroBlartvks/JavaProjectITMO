package org.AstroLab.utils.tcpProtocol;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonSerializer {
    <T> byte[] serializeToBytes(T response) throws JsonProcessingException;
}
