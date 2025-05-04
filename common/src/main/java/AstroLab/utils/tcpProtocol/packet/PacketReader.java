package AstroLab.utils.tcpProtocol.packet;

import java.nio.ByteBuffer;
import java.util.Date;
import lombok.Getter;

/**.
 * Reads raw bytes and extracts complete {@link Packet} instances.
 *
 * <p>
 * Usage (one reader per thread):
 *  * <pre>
 *  * PacketReader reader = new PacketReader();
 *  * reader.feed(bufferFromChannel);
 *  * while (reader.hasPacket()) {
 *  *     Packet packet = reader.nextPacket();
 *  *     // process packet.getData(), packet.getTimestamp(), etc.
 *  * }
 *  * </pre>
 *  * </p>
 */
@Getter
public class PacketReader {
    public static final int INITIAL_BUFFER_SIZE = 4096;
    private ByteBuffer buffer = ByteBuffer.allocate(INITIAL_BUFFER_SIZE);
    private final Crc32Checksum checksum = new Crc32Checksum();

    public void feed(ByteBuffer src) {
        ensureCapacity(src.remaining());
        buffer.put(src);
    }

    public boolean notFullPacket() {
        buffer.flip();
        int remaining = buffer.remaining();

        if (remaining < Packet.HEADER_SIZE) {
            buffer.compact();
            return true;
        }

        buffer.mark();

        int len = buffer.getInt();
        int need = Packet.HEADER_SIZE + len;

        buffer.reset();

        boolean ok = buffer.limit() >= need;

        buffer.compact();
        return !ok;
    }

    public Packet nextPacket() {
        buffer.flip();
        buffer.mark();

        int len = buffer.getInt();
        long hash = buffer.getLong();

        int need = Packet.HEADER_SIZE + len;

        if (buffer.limit() < need) {
            buffer.reset();
            buffer.compact();
            return null;
        }

        byte[] data = new byte[len];
        buffer.get(data);

        long calc = checksum.compute(data);
        if (calc != hash) {
            throw new IllegalStateException("Hash mismatch: " + calc + " != " + hash);
        }

        buffer.compact();

        long timeStamp = buffer.getLong();
        return new Packet(len, hash, new Date(timeStamp), data);
    }

    private void ensureCapacity(int inc) {
        if (buffer.remaining() < inc) {
            ByteBuffer nb = ByteBuffer.allocate(Math.max(buffer.capacity() * 2, buffer.capacity() + inc));
            buffer.flip();
            nb.put(buffer);
            buffer = nb;
        }
    }
}