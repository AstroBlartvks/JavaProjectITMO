package AstroLab.utils.tcpProtocol.packet;

import java.nio.ByteBuffer;
import java.util.Date;
import lombok.Getter;

/**.
 * Represents a TCP/IP-style packet with the following structure:
 * <pre>
 * [4 bytes: data length]
 * [8 bytes: data hash (CRC32 stored in 8-byte field)]
 * [8 bytes: timestamp in milliseconds since epoch]
 * [N bytes: payload data]
 * </pre>
 *
 * <p>
 * This class is thread-safe when used as an encoder (no shared mutable state). For decoding,
 * each thread should use its own {@link PacketReader} instance.
 * </p>
 */
@Getter
public class Packet {
    public static final int HEADER_SIZE = Integer.BYTES + Long.BYTES + Long.BYTES;

    private final int length;
    private final long hash;
    private final Date timestamp;
    private final byte[] data;

    /**
     * Constructs a packet instance after decoding.
     *
     * @param length    number of payload bytes
     * @param hash      CRC32 hash of the payload
     * @param timestamp packet creation time
     * @param data      payload bytes
     */
    public Packet(int length, long hash, Date timestamp, byte[] data) {
        this.length = length;
        this.hash = hash;
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     * Encodes payload into a ByteBuffer ready for transmission.
     * Timestamp is set to current time.
     *
     * @param data payload bytes
     * @return ByteBuffer containing full packet (position set to 0, limit at end)
     */
    public static ByteBuffer encode(byte[] data) {
        int len = data.length;
        long hash = new Crc32Checksum().compute(data);
        long now = new Date().getTime();

        int totalSize = HEADER_SIZE + len;
        ByteBuffer buffer = ByteBuffer.allocate(totalSize);
        buffer.putInt(len);
        buffer.putLong(hash);
        buffer.putLong(now);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Returns a copy of the payload data.
     *
     * @return payload bytes
     */
    public byte[] getData() {
        return data.clone();
    }
}