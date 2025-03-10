package com.novinsadr.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class CryptoUtils {
    public static String Reverse(String source, int MaxChars) {
        int len = source.length();
        StringBuffer dest = new StringBuffer(len);
        for (int i = len - 1; i >= 0; i--) {
            if (MaxChars > 0) {
                dest.append(source.charAt(i));
            }
            MaxChars--;
        }
        return dest.toString();
    }

    public static String xor(String str, String key) {
        String result = null;
        byte[] strBuf = str.getBytes();
        byte[] keyBuf = key.getBytes();
        int c = 0;
        int z = keyBuf.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(strBuf.length);
        for (byte bS : strBuf) {
            byte bK = keyBuf[c];
            byte bO = (byte) (bS ^ bK);
            if (c < z - 1) {
                c++;
            } else {
                c = 0;
            }
            baos.write(bO);
        }
        try {
            baos.flush();
            result = baos.toString();
            baos.close();
        } catch (IOException e) {
        }
        return result;
    }

    public static String Byte2Hex(byte[] b) throws Exception {
        String result = "";
        for (byte b2 : b) {
            result = String.valueOf(result) + Integer.toString((b2 & 255) + 256, 16).substring(1);
        }
        return result;
    }

    public static byte[] Hex2Byte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String OKM_Encode(String Text, String SALT) {
        String Result = "";
        char[] CharStr = Text.toUpperCase().toCharArray();
        for (int i = 0; i < CharStr.length; i++) {
            if (CharStr[i] != ':') {
                Result = String.valueOf(Result) + CharStr[i];
            }
        }
        String Result2 = xor(Result, SALT.toUpperCase());
        try {
            return Byte2Hex(Result2.getBytes("UTF-8"));
        } catch (Exception e) {
            return Result2;
        }
    }

    public static String OKM_Decode(String Text, String SALT) {
        byte[] ByteStr = Hex2Byte(Text.toUpperCase());
        String Result = "";
        for (byte b : ByteStr) {
            Result = String.valueOf(Result) + ((char) b);
        }
        char[] CharStr = xor(Result, SALT.toUpperCase()).toCharArray();
        String Result2 = "";
        for (int i = 0; i < CharStr.length; i++) {
            if (i > 0 && i % 2 == 0) {
                Result2 = String.valueOf(Result2) + ":";
            }
            Result2 = String.valueOf(Result2) + CharStr[i];
        }
        return Result2.toUpperCase();
    }

    public static String DecodeActivationCode(String ActivationCode, String SerialNumber) {
        byte[] ByteStr = Hex2Byte(ActivationCode.toUpperCase());
        String Result = "";
        for (byte b : ByteStr) {
            Result = String.valueOf(Result) + ((char) b);
        }
        String ENC = "";
        for (int i = 0; i < Globals.ENC_BYTES.length; i++) {
            ENC = String.valueOf(ENC) + Globals.ENC_BYTES[i];
        }
        char[] CharStr = xor(xor(Result, ENC), SerialNumber.toUpperCase()).toCharArray();
        String Result2 = "";
        for (int i2 = 0; i2 < CharStr.length; i2++) {
            if (i2 > 0 && i2 % 2 == 0) {
                Result2 = String.valueOf(Result2) + ":";
            }
            Result2 = String.valueOf(Result2) + CharStr[i2];
        }
        return Result2.toUpperCase();
    }

    public static boolean Compare(String BT, String EncodedStr) throws Exception {
        return Encode(BT) == EncodedStr;
    }

    public static String Encode(String value) throws Exception {
        return byteArrayToHexString(computeHash(value));
    }

    public static byte[] computeHash(String x) throws Exception {
        MessageDigest d = MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (byte b2 : b) {
            int v = b2 & 255;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}
