package org.AstroLab.utils.tcpProtocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class TcpProtocol implements Sender, Receiver, JsonSerializer, JsonDeserializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T deserializeFromBytes(byte[] bytes, Class<T> type) throws IOException {
        String message = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)).toString().trim();
        return objectMapper.readValue(message, type);
    }

    @Override
    public <T> byte[] serializeToBytes(T response) throws JsonProcessingException  {
        String json = new ObjectMapper().writeValueAsString(response) + "\n";
        return json.getBytes(StandardCharsets.UTF_8);
    }

}
