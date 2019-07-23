package com.li.honedu.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class AESUtil {
    private AESUtil() {
    }

    public static String encrypt(String secret, String value) {
        SecretKeySpec keySpec = getKey(secret);
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, keySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return parseByte2HexStr(encrypted);
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static String decrypt(String secret, String value) {
        SecretKeySpec keySpec = getKey(secret);
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, keySpec, iv);
            byte[] encrypted1 = parseHexStr2Byte(value);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    private static SecretKeySpec getKey(String secret) {
        try {
            byte[] bytes = secret.getBytes("UTF-8");
            return new SecretKeySpec(Arrays.copyOf(bytes, 16), "AES");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }
}
