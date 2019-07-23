package com.li.honedu.util;

import java.io.UnsupportedEncodingException;

public class SignUtil {
    public SignUtil() {
    }

    public static String signMD5(String srcStr, String appsecret) {
        String dist = null;
        String ds = "appsecret" + srcStr + appsecret;

        try {
            dist = DigestUtil.md5Hex(ds.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        return dist.toUpperCase();
    }

    public static boolean verifySignMD5(String src, String appKey, String sign) {
        boolean isOk = false;

        try {
            String signNew = signMD5(src, appKey);
            if (signNew.equals(sign)) {
                isOk = true;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return isOk;
    }
}
