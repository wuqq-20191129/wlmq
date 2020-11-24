package com.goldsign.csfrm.util;

public class HexStr {

    public static String stringToHex(String s) {
        byte[] stringBytes = s.getBytes();
        return HexStr.bufferToHex(stringBytes);
    }

    public static String bufferToHex(byte buffer[]) {
        return HexStr.bufferToHex(buffer, 0, buffer.length).toUpperCase();
    }

    public static String bufferToHex(byte buffer[], int startOffset, int length) {
        StringBuffer hexString = new StringBuffer(2 * length);
        int endOffset = startOffset + length;
        for (int i = startOffset; i < endOffset; i++) {
            HexStr.appendHexPair(buffer[i], hexString);
        }
        return hexString.toString().toUpperCase();
    }

    public static String hexToString(String hexString)
            throws NumberFormatException {
        byte[] bytes = HexStr.hexToBuffer(hexString);
        return new String(bytes);
    }

    public static byte[] hexToBuffer(String hexString)
            throws NumberFormatException {
        int length = hexString.length();
        byte[] buffer = new byte[(length + 1) / 2];
        boolean evenByte = true;
        byte nextByte = 0;
        int bufferOffset = 0;

        if ((length % 2) == 1) {
            evenByte = false;
        }
        for (int i = 0; i < length; i++) {
            char c = hexString.charAt(i);
            int nibble;
            if ((c >= '0') && (c <= '9')) {
                nibble = c - '0';
            } else if ((c >= 'A') && (c <= 'F')) {
                nibble = c - 'A' + 0x0A;
            } else if ((c >= 'a') && (c <= 'f')) {
                nibble = c - 'a' + 0x0A;
            } else {
                throw new NumberFormatException("Invalid hex digit '" + c
                        + "'.");
            }

            if (evenByte) {
                nextByte = (byte) (nibble << 4);
            } else {
                nextByte += (byte) nibble;
                buffer[bufferOffset++] = nextByte;
            }
            evenByte = !evenByte;
        }
        return buffer;
    }

    private static void appendHexPair(byte b, StringBuffer hexString) {
        char highNibble = kHexChars[(b & 0xF0) >> 4];
        char lowNibble = kHexChars[b & 0x0F];
        hexString.append(highNibble);
        hexString.append(lowNibble);
    }

    /**
     * pad to the left
     *
     * @param s - original string
     * @param len - desired len
     * @param c - padding char
     * @return padded string
     */
    public static String padleft(String s, int len, char c) {
        s = s.trim();
        if (s.length() > len) {
            throw new NumberFormatException("invalid len " + s.length() + "/"
                    + len);
        }
        StringBuffer d = new StringBuffer(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }

    /**
     * left pad with '0'
     *
     * @param s - original string
     * @param len - desired len
     * @return zero padded string
     */
    public static String zeropad(String s, int len) {
        return padleft(s, len, '0');
    }

    public static String longToHex(long l, int len) {
        return zeropad(Long.toHexString(l).toUpperCase(), len).toUpperCase();
    }

    // 将字符转化为字节
    public static byte[] charToByte(char ch) {
        int temp = (int) ch;
        byte[] b = new byte[2];
        // 将高8位放在b[0],将低8位放在b[1]
        for (int i = 1; i > -1; i--) {
            b[i] = (byte) (temp & 0xFF);
            // 向右祿8仿
            temp >>= 8;
        }
        return b;
    }

    // 将字节转化为字符
    public static char byteToChar(byte[] b) {
        int s = 0;
        if (b[0] > 0) {
            s += b[0];
        }
        if (b[0] < 0) {
            s += 256 + b[0];
        }
        s *= 256;
        if (b[1] > 0) {
            s += b[1];
        }
        if (b[1] < 0) {
            s += 256 + b[1];
        }
        char ch = (char) s;
        return ch;
    }

    // 将字节转化为比特数组
    public static byte[] bitToByteArray(byte b) {
        // 强制转换成int?
        int temp = (int) b;
        byte[] result = new byte[8];
        for (int i = 7; i > -1; i--) {
            result[i] = (byte) (temp & 0x01);
            temp >>= 1;
        }
        return result;
    }

    /**
     * 将二维比特数组转化为字节数组
     *
     * @param b
     * @return
     */
    public static byte byteToBitArray(byte[] b) {
        byte result;

        result = (byte) (b[7] | b[6] << 1 | b[5] << 2 | b[4] << 3 | b[3] << 4
                | b[2] << 5 | b[1] << 6 | b[0] << 7);

        return result;
    }

    /**
     * 宿 "1234567890123456" 型的16进制数字 转成 char[4]
     *
     * @param hexStr
     * @return
     */
    public static char[] hexStrToChar4(String hexStr) {

        char[] hexChar = new char[4];
        byte[] a = HexStr.hexToBuffer(hexStr);
        byte[] a1 = new byte[2];
        byte[] a2 = new byte[2];
        byte[] a3 = new byte[2];
        byte[] a4 = new byte[2];
        System.arraycopy(a, 0, a1, 0, 2);
        System.arraycopy(a, 2, a2, 0, 2);
        System.arraycopy(a, 4, a3, 0, 2);
        System.arraycopy(a, 6, a4, 0, 2);
        hexChar[0] = HexStr.byteToChar(a1);
        hexChar[1] = HexStr.byteToChar(a2);
        hexChar[2] = HexStr.byteToChar(a3);
        hexChar[3] = HexStr.byteToChar(a4);
        return hexChar;
    }

    /**
     * 宿 char[4] 转成 16进制 数字
     *
     * @param hexChar
     * @return
     */
    public static String char4ToHexStr(char[] hexChar) {
        byte[] result = new byte[8];
        byte[] temp = new byte[2];
        for (int i = 0; i < 4; i++) {
            temp = HexStr.charToByte(hexChar[i]);
            System.arraycopy(temp, 0, result, 2 * i, 2);
        }
        return HexStr.bufferToHex(result);
    }
    private static final char kHexChars[] = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
