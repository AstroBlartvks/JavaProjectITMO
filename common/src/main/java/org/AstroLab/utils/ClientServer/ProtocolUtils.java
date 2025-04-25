package org.AstroLab.utils.ClientServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ProtocolUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ByteBuffer serializeToBuffer(Object response) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(response) + "\n";
        return ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
    }

    public static <T> T deserializeFromBuffer(ByteBuffer buffer, Class<T> type) throws IOException {
        String message = StandardCharsets.UTF_8.decode(buffer).toString().trim();
        return objectMapper.readValue(message, type);
    }

    public static ByteBuffer readCompleteMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new IOException("Connection closed by client");
            } else if (bytesRead == 0) {
                break;
            }
            if (!buffer.hasRemaining()) {
                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                buffer.flip();
                newBuffer.put(buffer);
                buffer = newBuffer;
            }
        }
        buffer.flip();
        return buffer;
    }
}