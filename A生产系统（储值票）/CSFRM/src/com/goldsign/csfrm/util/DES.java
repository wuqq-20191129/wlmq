package com.goldsign.csfrm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES加解密类
 *
 * @author zmj
 *
 */
public class DES {

    protected SecretKey key;

    public DES(String key) {
        setKey(key);
    }

    public void setKey(String hexStrKey) {

        byte[] rawKeyData = HexStr.hexToBuffer(hexStrKey); // 璿16进制数作密钥
        //0123456789ABCDEF等价丿0x01,0x23,0x45,0x67,0x89,(byte)0xab,(byte)0xcd,(byte)0xef
        //byte[] rawKeyData2=new byte[]{0x01,0x23,0x45,0x67,(byte)0x89,(byte)0xAB,(byte)0xCD,(byte)0xEF};
        try {
            DESKeySpec dks = new DESKeySpec(rawKeyData);
            // 创建䶿个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(dks);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public String getEncStringNobase64(String strMing) {
        if (strMing == null) {
            throw new IllegalArgumentException("明文不能为空");
        }
        String hexSrc = "";
        for (int i = 0; i < strMing.length(); i++) {
            String s = Integer.toHexString((int) strMing.charAt(i));
            if (s.length() < 2) {
                s = "0" + s;
            }
            hexSrc += s;
        }
        System.out.println(hexSrc);
        while (hexSrc.length() == 0 || hexSrc.length() % 16 != 0) {
            hexSrc += "0";
        }
        return getEncHexStr(hexSrc);
    }

    public String getDesStringNobase64(String strMi) {
        if (strMi == null) {
            throw new IllegalArgumentException("密文不能为空");
        }
        String ming = getDesHexStr(strMi);
        int len = ming.length();
        int bwLen = 0;
        for (int k = 0; k < 8; k++) {
            if (!"00".equals(ming.substring(len - 2 * k - 2, len - 2 * k - 1))) {
                break;
            }
            bwLen++;
        }
        String hexSrc = ming.substring(0, len - bwLen * 2);

        StringBuffer srcSb = new StringBuffer();
        for (int i = 0; i < hexSrc.length() - 1; i = i + 2) {
            srcSb.append((char) (Integer.parseInt(hexSrc.substring(i, i + 2), 16)));
        }
        return srcSb.toString();
    }

    public byte[] getEncByteNobase64(byte[] bMing) {
        if (bMing == null) {
            throw new IllegalArgumentException("明文不能为空");
        }
        int len = bMing.length;
        while (bMing.length == 0 || len % 8 != 0) {
            len++;
        }
        byte[] jmMing = new byte[len];
        System.arraycopy(bMing, 0, jmMing, 0, bMing.length);
        for (int i = bMing.length; i < len; i++) {
            jmMing[i] = 0x00;
        }
        /*
         * String hexSrc=""; for(int i =0;i<bMing.length;i++) { String
         * s=Integer.toHexString(bMing[i]&0xff); if(s.length()<2) { s="0"+s; }
         * hexSrc+=s; } while(hexSrc.length()==0||hexSrc.length()%16!=0) {
         * hexSrc+="0"; }
         */
        return getEncCode(jmMing);
    }

    public byte[] getDesByteNobase64(byte[] bMi) {
        if (bMi == null) {
            throw new IllegalArgumentException("密文不能为空");
        }
        byte[] byteMing = this.getDesCode(bMi);
        int len = byteMing.length;
        int bwLen = 0;
        for (int k = 0; k < 8; k++) {
            if (0x00 != byteMing[len - bwLen - 1]) {
                break;
            }
            bwLen++;
        }
        byte[] ret = new byte[len - bwLen];
        System.arraycopy(byteMing, 0, ret, 0, len - bwLen);
        return ret;
        /*
         * String strMing = HexStr.bufferToHex(byteMing);
         *
         * String strMi = new String(bMi); String ming=getDesHexStr(strMi);
         * String hexSrc=""; for(int i=0;i<ming.length() -1;i=i+2) {
         * if(!"00".equals(ming.substring(i,i+2))){
         * hexSrc+=ming.substring(i,i+2); } } StringBuffer srcSb=new
         * StringBuffer(); for(int i=0;i<hexSrc.length()-1;i=i+2) {
         * srcSb.append((char)(Integer.parseInt(hexSrc.substring(i,i+2), 16)));
         * } return srcSb.toString().getBytes();
         */
    }

    /**
     * 加密String明文输入,String密文输出
     */
    public String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String getDesString(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException("UnsupportedEncodingException: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密16进制串明文输僿,16进制串密文输兿
     */
    public String getEncHexStr(String hexStrMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        byteMing = HexStr.hexToBuffer(hexStrMing);

        byteMi = this.getEncCode(byteMing);
        strMi = HexStr.bufferToHex(byteMi);

        return strMi;
    }

    public String getEncPre8HexStr(String hexStrMing) {
        String strMi = getEncHexStr(hexStrMing);
        if (strMi.length() > 16) {
            strMi = strMi.substring(0, 16);
        }
        return strMi;
    }

    /**
     * 解密 16进制串密文输僿,16进制串明文输兿
     */
    public String getDesHexStr(String hexStrMi) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMing = "";
        byteMi = HexStr.hexToBuffer(hexStrMi);

        byteMing = this.getDesCode(byteMi);
        strMing = HexStr.bufferToHex(byteMing);

        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    public byte[] getEncCode(byte[] data) {
        byte[] encryptedData = null;

        try {
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // 执行加密操作
            encryptedData = cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return encryptedData;

    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    public byte[] getDesCode(byte[] data) {
        byte[] decryptedData = null;
        try {
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 执行加密操作
            decryptedData = cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedData;
    }

    private static void testAll() throws IOException {
        DES des = new DES("0123456789ABCDEF");
        String ming = "1111111122222222";
        String mi = des.getEncHexStr(ming);
        System.out.println("16进制加密后密文\n|" + mi + "|");
        ming = des.getDesHexStr(mi);
        System.out.println("16进制解密\n|" + ming + "|");
        ming = "9999900000022173000100;9999900000022173=2012725339?>;999999900000022173=5059853?629541" + "\r\n"
                + "9999900000038857000100;9999900000038857=2012725555?6;999999900000038857=5752041?340628" + "\r\n";
        //ming="12345678";
        mi = des.getEncString(ming);
        System.out.println("base64加密后密文\n|" + mi + "|");
        ming = des.getDesString(mi);
        System.out.println("base64解密\n|" + ming + "|");

        File f1 = new File("c:/HexAdd8Byte.txt");
        File f2 = new File("c:/bytesAdd8Btye.txt");
        File f3 = new File("c:/bytes.txt");
        File f4 = new File("c:/Hex.txt");
        FileOutputStream fs = null;

        mi = des.getEncStringNobase64(ming);
        System.out.println("字符串不用base64加密后密文\n|" + mi + "|");
        ming = des.getDesStringNobase64(mi);
        System.out.println("字符串不用base64解密\n|" + ming + "|");

        fs = new FileOutputStream(f1);
        fs.write(mi.getBytes());
        fs.flush();
        fs.close();

        byte[] hexMi = new byte[mi.getBytes().length - 16];
        System.arraycopy(mi.getBytes(), 0, hexMi, 0, mi.getBytes().length - 16);
        fs = new FileOutputStream(f4);
        fs.write(hexMi);
        fs.flush();
        fs.close();

        ming = "9999900000022173000100;9999900000022173=2012725339?>;999999900000022173=5059853?629541" + "\r\n"
                + "9999900000038857000100;9999900000038857=2012725555?6;999999900000038857=5752041?340628" + "\r\n";
        //ming="12345678";
        //不做转换
        byte[] bmi = des.getEncByteNobase64(ming.getBytes());
        System.out.println("直接加密返回byte，加密|" + new String(bmi) + "|");
        fs = new FileOutputStream(f2);
        fs.write(bmi);
        fs.flush();
        fs.close();
        byte[] bming = des.getDesByteNobase64(bmi);
        System.out.println("直接加密返回byte，解密|" + new String(bming) + "|");

        byte[] bmi2 = new byte[bmi.length - 8];
        System.arraycopy(bmi, 0, bmi2, 0, bmi.length - 8);
        System.out.println("直接加密返回byte，加密去掉后雿8位|" + new String(bmi2) + "|");
        fs = new FileOutputStream(f3);
        fs.write(bmi2);
        fs.flush();
        fs.close();
    }

    public static void main(String args[]) throws IOException {

        //testAll();
        //导出制卡文件的密文例嫿
        //密钥从配置文件读出来⾿
        DES des = new DES("012345BCDEF6789A");
        System.out.println(des.getEncString("zjj123"));
        //明文
        String ming = "9999900000022173000100;9999900000022173=2012725339?>;999999900000022173=5059853?629541" + "\r\n"
                + "9999900000038857000100;9999900000038857=2012725555?6;999999900000038857=5752041?340628" + "\r\n";

        //导出的文乿
        File f1 = new File("c:/bytes.txt");
        FileOutputStream fs = null;

        //加密出的结果
        byte[] bmi = des.getEncByteNobase64(ming.getBytes());
        //System.out.println("加密|"+new String(bmi)+"|");

        //暿后写文件的密文要去掉县8个字舿
        byte[] bmi2 = new byte[bmi.length - 8];

        System.arraycopy(bmi, 0, bmi2, 0, bmi.length - 8);
        fs = new FileOutputStream(f1);
        fs.write(bmi2);
        fs.flush();
        fs.close();

        byte[] bming = des.getDesByteNobase64(bmi);
        System.out.println("解密|" + new String(bming) + "|");
    }
}