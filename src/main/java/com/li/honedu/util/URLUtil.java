package com.li.honedu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLUtil {
    public URLUtil() {
    }

    public static List<String> getRequestUrlParamValues(String URL) {
        List<String> list = new ArrayList();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return null;
        } else {
            arrSplit = strUrlParam.split("[&]");
            String[] var4 = arrSplit;
            int var5 = arrSplit.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String strSplit = var4[var6];
                String[] arrSplitEqual = null;
                arrSplitEqual = strSplit.split("[=]");
                if (arrSplitEqual.length > 1) {
                    list.add(arrSplitEqual[1]);
                } else if (!"".equals(arrSplitEqual[0])) {
                    list.add("");
                }
            }

            return list;
        }
    }

    public static Map<String, String> getRequestUrlParams(String URL) {
        Map<String, String> mapRequest = new HashMap();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        } else {
            arrSplit = strUrlParam.split("[&]");
            String[] var4 = arrSplit;
            int var5 = arrSplit.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String strSplit = var4[var6];
                String[] arrSplitEqual = null;
                arrSplitEqual = strSplit.split("[=]");
                if (arrSplitEqual.length > 1) {
                    mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
                } else if (!"".equals(arrSplitEqual[0])) {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }

            return mapRequest;
        }
    }

    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1 && arrSplit.length > 1 && arrSplit[1] != null) {
            strAllParam = arrSplit[1];
        }

        return strAllParam;
    }
}
