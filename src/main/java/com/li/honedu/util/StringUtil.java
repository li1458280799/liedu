package com.li.honedu.util;


import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
    public StringUtil() {
    }

    public static String getNextCode(String code) {
        if (code != null && !code.equals("")) {
            try {
                int idx = -1;
                if (code.charAt(code.length() - 1) >= '0' && code.charAt(code.length() - 1) <= '9') {
                    int no9Idx = 0;

                    for(int i = code.length() - 1; i >= 0; --i) {
                        if (code.charAt(i) < '0' || code.charAt(i) > '9') {
                            idx = i;
                            break;
                        }

                        if (code.charAt(i) != '9') {
                            ++no9Idx;
                        }

                        if (no9Idx >= 2) {
                            idx = i;
                            break;
                        }
                    }

                    String code1 = "";
                    String code2 = "";
                    if (idx > -1) {
                        code1 = code.substring(0, idx + 1);
                        code2 = code.substring(idx + 1);
                    } else {
                        code1 = "";
                        code2 = code;
                    }

                    String code2chg = String.valueOf(Long.parseLong(code2) + 1L);
                    if (code2chg.length() < code2.length()) {
                        for(int i = 0; i <= code2.length() - code2chg.length(); ++i) {
                            code2chg = "0" + code2chg;
                        }
                    }

                    return code1 + code2chg;
                } else {
                    return code + "1";
                }
            } catch (Exception var7) {
                return code;
            }
        } else {
            return "";
        }
    }
}
