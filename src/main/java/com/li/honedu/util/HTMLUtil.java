package com.li.honedu.util;

import java.util.List;

public class HTMLUtil {
    public HTMLUtil() {
    }

    public static List<String> getImgStr(String html) {
        List<String> all = RegexUtil.findAllIgnoreCase("<img.*src=(.*?)[^>]*?>", html);
        String img = StringUtil.join(all, ",");
        List<String> pics = RegexUtil.findAllIgnoreCase("src=\"?(.*?)(\"|>|\\s+)", img, 1);
        return pics;
    }
}
