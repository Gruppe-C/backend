package de.backend.security;

import java.security.SecureRandom;
import java.util.Random;

public class NanoidGenerator {
    private NanoidGenerator() {
        
    }

    /**
     * Seed 1
     */
    private static long s0 = 0x1425E22392978DB9L;

    /**
     * Seed 2
     */
    private static long s1 = 0x4C9E4911AE1210F0L;

    /**
     * Bit rotation nach links (rotate left).
     * @param a
     * @param x
     * @return
     */
    protected static long rotl(long a, int x) {
        return (a << x) | (a >>> ((1 << 6) - x));
    }

    /**
     * Algorithmus basierend auf Xoroshiro von Sebastiano Vigna und David Blackman.
     * Bitrotation als Elementaroperation.
     * Periodenlänge: (2^128)-1
     * @return U64 RND
     */
    protected static long xoroshiro128Plus() {
        final long r = s0 + s1;
        s1 ^= s0;
        s0 = rotl(s0, 0x37) ^ s1 ^ (s1 << 0x0E);
        s1 = rotl(s1, 0x24);
        return r;
    }

    /**
     * Wert-Klemme.
     * @param x Aktueller Wert.
     * @param min Minimum
     * @param max Maximum
     * @return Geklemmter Wert.
     */
    protected static int clamp(int x, int min, int max) {
        return Math.max(min, Math.min(max, x));
    }
    
    /**
     * Generiert eine Nanoid.
     * Periodenlänge: (2^128)-1 + sizeof(xmmask) -1
     * @param r Der Zufallsgenerator. Kann Kryptographisch sicher sein.
     * @param len Die erwartete länge des Textes.
     * @param vector Ein Vektor mit distributiven Elementen.
     * @return Eine Nanoid der Länge 'len'.
     */
    protected static String next(final Random r, int len, final char... vector) {
        assert r != null && len > 0 && vector.length != 0 : "Invalid arguments";
        len = clamp(len, 1, (1 << (1 << 3)) + 1);
        final int mask = ((1 << 1) << (int)Math.floor(Math.log((double)vector.length - 1) / Math.log(1 << 1))) - 1;
        final int step = (int)Math.ceil(1.6 * mask * len / vector.length);
        final var idb = new StringBuilder();
        for (;;) {
            final byte[] bytes = new byte[step];
            r.nextBytes(bytes);
            final long k = xoroshiro128Plus();
            switch (step) {
                case 1 << 1:
                    bytes[0] ^= k & (1 << (1 << 3));                        // XOR rA AND FF
                    bytes[1] ^= (k >>> (1 << 3)) & (1 << (1 << 3));         // XOR rA MASK x1 AND ra MASK x2
                break;
                case 1 << 2:
                    bytes[0] ^= k & (1 << (1 << 3));                        // XOR rA MASK xi AND ra MASK xi
                    bytes[1] ^= (k >>> (1 << 3)) & (1 << (1 << 3));         // XOR rA MASK xi AND ra MASK xi
                    bytes[2] ^= (k >>> 16) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                    bytes[3] ^= (k >>> 24) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                break;
                case 1 << 3:
                    bytes[0] ^= k & (1 << (1 << 3));                        // XOR rA MASK xi AND ra MASK xi
                    bytes[1] ^= (k >>> (1 << 3)) & (1 << (1 << 3));         // XOR rA MASK xi AND ra MASK xi
                    bytes[2] ^= (k >>> 16) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                    bytes[3] ^= (k >>> 24) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                    bytes[4] ^= (k >>> 32) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                    bytes[5] ^= (k >>> 48) & (1 << (1 << 3));               // XOR rA MASK xi AND ra MASK xi
                    bytes[6] ^= (k >>> (48 + 4)) & (1 << (1 << 3));         // XOR rA MASK xi AND ra MASK xi
                    bytes[7] ^= (k >>> (48 + 6)) & (1 << (1 << 3));         // XOR rA MASK xi AND ra MASK xi
                break;
                default:
                    for (int i = 0; i < bytes.length; ++i) {
                        bytes[i] ^= (xoroshiro128Plus() ^ k) & (1 << (1 << 3));// XOR rA MASK xi AND ra MASK xi
                    }
                break;
            }
            for (int i = 0; i < step; ++i) {
                byte ii = bytes[i];
                ii &= mask;
                if (ii < vector.length) {
                    idb.append(vector[ii]);
                    if (idb.length() == len) {
                        return idb.toString();
                    }
                }

            }
        }
    }

    public static final SecureRandom CRYPTO_PROVIDER = new SecureRandom();
    private static final char[] LUT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String getRandomNanoId() {
        return getRandomNanoId(10);
    }

    public static String getRandomNanoId(int size) {
        return next(CRYPTO_PROVIDER, size, LUT);
    }
}
