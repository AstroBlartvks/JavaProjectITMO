package org.AstroLab.utils.tcpProtocol.packet;


import java.util.zip.CRC32;

/**
 * Utility to compute CRC32 checksums.
 */
class CRC32Checksum {
    /**
     * Computes CRC32 over given bytes.
     *
     * @param data input bytes
     * @return 32-bit CRC value as 64-bit long
     */
    public static long compute(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }
}
