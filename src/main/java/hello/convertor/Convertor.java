/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello.convertor;


import java.util.BitSet;

/**
 *
 * @author kami
 */

public class Convertor {

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    /**
     * Convert number from int to byte[] and padding with 0
     * @param len
     * @return
     */
    public static byte[] getByteLEN(int len) {
        String l = len + "";
        for (int i = 0; i < 4 - l.length(); i++) {
            l = "0" + l;
        }
        System.out.println(l);
        return l.getBytes();
    }

    /**
     * Convert 8 byte hex string to int[64]
     * @param n1 first 8 hex char
     * @param n2 second 8 hex char
     * @return
     */
    public static int[] hexToBinary(String n1, String n2) {
        int[] data = new int[65];
        String str1, str2;
        str1 = Long.toBinaryString(Long.parseLong("1" + n1, 16));
        str2 = Long.toBinaryString(Long.parseLong("1" + n2, 16));
        str1 = str1.substring(1, 33);
        str2 = str2.substring(1, 33);
        str1 += str2;
        for (int i = 0; i < 64; i++) {
            data[i + 1] = Integer.parseInt(str1.substring(i, i + 1));
        }
        return data;
    }

    /**
     * Convert byte[] to hex string
     * @param in
     * @return
     *this method is deprecated use convertBytesToHex
     * it is faster and  optimized
     */
    @Deprecated
    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString().toUpperCase();
    }
    /**
     * Converts an array of bytes to its hexadecimal representation.
     *
     * @param in The array of bytes to convert.
     * @return The hexadecimal representation of the input array.
     */
    public static String convertBytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(HEX_CHARS[(b & 0xF0) >>> 4]);
            builder.append(HEX_CHARS[b & 0x0F]);
        }
        return builder.toString();
    }


    /**
     * Convert hex string to byte[]
     * @param s
     * @return
     * this method is deprecated use convertHexStringToByteArray
     * it is faster and  optimized
     */
    @Deprecated
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    /**
     * Converts a hexadecimal string representation to an array of bytes.
     *
     * @param s the hexadecimal string to be converted
     * @return the resulting array of bytes
     */
    public static byte[] convertHexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        char[] chars = s.toCharArray();
        for (int i = 0, j = 0; i < len; i += 2, j++) {
            int high = hexToInt(chars[i]);
            int low = hexToInt(chars[i + 1]);
            data[j] = (byte) ((high << 4) + low);
        }
        return data;
    }

    private static final int[] HEX_VALUES = new int[128];

    static {
        for (int i = 0; i < 10; i++) {
            HEX_VALUES['0' + i] = i;
        }
        for (int i = 10; i < 16; i++) {
            HEX_VALUES['A' + i - 10] = i;
            HEX_VALUES['a' + i - 10] = i;
        }
    }

    /**
     * Converts a hexadecimal character to its corresponding integer value.
     *
     * @param c the hexadecimal character to be converted
     * @return the corresponding integer value
     */
    private static int hexToInt(char c) {
        return c < 128 ? HEX_VALUES[c] : 0;
    }

    /**
     *
     * @param b
     * @return
     */
    public static byte[] bitSet2byte(BitSet b) {
        int len = b.length() + 62 >> 6 << 6;
        byte[] d = new byte[len >> 3];
        for (int i = 0; i < len; ++i) {
            if (!b.get(i + 1)) {
                continue;
            }
            byte[] arrby = d;
            int n = i >> 3;
            arrby[n] = (byte) (arrby[n] | 128 >> i % 8);
        }
        if (len > 64) {
            byte[] arrby = d;
            arrby[0] = (byte) (arrby[0] | 128);
        }
        if (len > 128) {
            byte[] arrby = d;
            arrby[8] = (byte) (arrby[8] | 128);
        }
        return d;
    }

    /**
     *
     * @param bmap
     * @param b
     * @param bitOffset
     * @return
     */
    public static BitSet byte2BitSet(BitSet bmap, byte[] b, int bitOffset) {
        int len = b.length << 3;
        for (int i = 0; i < len; ++i) {
            if ((b[i >> 3] & 128 >> i % 8) <= 0) {
                continue;
            }
            bmap.set(bitOffset + i + 1);
        }
        return bmap;
    }

    /**
     *
     * @param b
     * @param offset
     * @param bitZeroMeansExtended
     * @return
     */
    public static BitSet hex2BitSet(byte[] b, int offset, boolean bitZeroMeansExtended) {
        int len = bitZeroMeansExtended ? ((Character.digit((char) b[offset], 16) & 8) == 8 ? 128 : 64) : 64;
        BitSet bmap = new BitSet(len);
        for (int i = 0; i < len; ++i) {
            int digit = Character.digit((char) b[offset + (i >> 2)], 16);
            if ((digit & 8 >> i % 4) <= 0) {
                continue;
            }
            bmap.set(i + 1);
        }
        return bmap;
    }

    /**
     *
     * @param b
     * @param offset
     * @param maxBits
     * @return
     */
    public static BitSet hex2BitSet(byte[] b, int offset, int maxBits) {
        int len = maxBits > 64 ? ((Character.digit((char) b[offset], 16) & 8) == 8 ? 128 : 64) : maxBits;
        BitSet bmap = new BitSet(len);
        for (int i = 0; i < len; ++i) {
            int digit = Character.digit((char) b[offset + (i >> 2)], 16);
            if ((digit & 8 >> i % 4) <= 0) {
                continue;
            }
            bmap.set(i + 1);
            if (i != 65 || maxBits <= 128) {
                continue;
            }
            len = 192;
        }
        return bmap;
    }
}
