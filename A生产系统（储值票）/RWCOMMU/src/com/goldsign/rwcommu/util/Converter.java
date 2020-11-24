/* NFCard is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 3 of the License, or
 (at your option) any later version.

 NFCard is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Wget.  If not, see <http://www.gnu.org/licenses/>.

 Additional permission under GNU GPL version 3 section 7 */
package com.goldsign.rwcommu.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Converter {

    private final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private Converter() {
    }

    public static byte[] toBytes(int a) {
        return new byte[]{
                    (byte) (0x000000ff & (a)),
                    (byte) (0x000000ff & (a >>> 8)),
                    (byte) (0x000000ff & (a >>> 16)),
                    (byte) (0x000000ff & (a >>> 24)),};
    }

    public static byte[] to2Byte(int a) {
        return new byte[]{(byte) (0x000000ff & (a)),
                    (byte) (0x000000ff & (a >>> 8))};
    }

    public static byte[] longToBytes(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static byte[] longToByteS(long number) {
        long temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static int toInt(byte[] b, int s, int n) {
        int ret = 0;

        final int e = s + n;
        for (int i = s; i < e; ++i) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }

    public static int toIntR(byte[] b, int s, int n) {
        int ret = 0;

        for (int i = s; (i >= 0 && n > 0); --i, --n) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }

    public static int toInt(byte... b) {
        int ret = 0;
        for (final byte a : b) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
    }

    public static int to2Int(byte... b) {
        int ret = 0;
        for (final byte a : b) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
    }

    public static String toHexString(byte[] d, int s, int n) {
        final char[] ret = new char[n * 2];
        final int e = s + n;

        int x = 0;
        for (int i = s; i < e; ++i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    public static String toHexStringR(byte[] d, int s, int n) {
        final char[] ret = new char[n * 2];

        int x = 0;
        for (int i = s + n - 1; i >= s; --i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    public static int parseInt(String txt, int radix, int def) {
        int ret;
        try {
            ret = Integer.valueOf(txt, radix);
        } catch (Exception e) {
            ret = def;
        }

        return ret;
    }

    public static String toAmountString(float value) {
        return String.format("%.2f", value);
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    // 字符序列转换为16进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString().toUpperCase();
    }
    // 字符序列转换为16进制字符串

    public static String bytesToHexStringNoSpace(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    // 字符序列转换为16进制字符串
    public static String bytesToAsciiString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = new Character((char) v).toString();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString().toUpperCase();
    }
    // 字符序列转换为16进制字符串

    public static String bytesToAsciiStringNoSpace(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = new Character((char) v).toString();
//			if (hv.length() < 2) {
//				stringBuilder.append(0);
//			}
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    // byte数组转成long
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;// 最低位

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    public static int intFromByte(byte[] temp) {
        int value = 0;
        // value = (((temp[0] & 0xff) << 24) | ((temp[1] & 0xff) << 16) |
        // ((temp[2] & 0xff) << 8) | (temp[3] & 0xff));
        value = ((temp[0] & 0xff) | ((temp[1] & 0xff) << 8)
                | ((temp[2] & 0xff) << 16) | ((temp[3] & 0xff) << 24));
        return value;
    }

    // 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
    public static int toInts(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);

        }

        return iOutcome;
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static long unsigned4BytesToInt(byte[] buf, int pos) {
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        int index = pos;
        firstByte = (0x000000FF & buf[index]);
        secondByte = (0x000000FF & buf[index + 1]);
        thirdByte = (0x000000FF & buf[index + 2]);
        fourthByte = (0x000000FF & buf[index + 3]);
        index = index + 4;
        return (firstByte << 24 | secondByte << 16
                | thirdByte << 8 | fourthByte) & 0xFFFFFFFFL;
    }
    
    public static byte number = (byte) 0x00;   //起始序列号

    public static byte getNumber() {    //返回当前序列号
        if (number == 127) {
            number = (byte) 0x00;
        }
        return number++;
    }
    
    public static String curDateToStrYYYYMMDDHHMMSS(){
    
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date());
        
    }
    
      public static String getBcdString(byte[] data, int offset, int length){
	        StringBuilder sb = new StringBuilder();
	        try {
	                for (int i = 0; i < length; i++) {
	                        // logger.info("bcd之前："+data[offset + i]);
	                        sb.append(byte1ToBcd2(data[offset + i]));
	                       // logger.info("bcd之后："+byte1ToBcd2(data[offset + i]));
	                }
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	        return sb.toString();
	 }
     
     public static String byte1ToBcd2(byte b) {
	         int i = 0;
	         if (b < 0) {
	                 i = 256 + b;
	         } else {
	                 i = b;
	         }
	         return (new Integer(i / 16)).toString()
	                        + (new Integer(i % 16)).toString();
	 }
    
}
