package com.li.honedu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static final String regExp_integer_1 = "^\\d+$";
    public static final String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$";
    public static final String regExp_integer_3 = "^((-\\d+) ?(0+))$";
    public static final String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$";
    public static final String regExp_integer_5 = "^-?\\d+$";
    public static final String regExp_float_1 = "^\\d+(\\.\\d+)?$";
    public static final String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";
    public static final String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";
    public static final String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";
    public static final String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$";
    public static final String regExp_letter_1 = "^[A-Za-z]+$";
    public static final String regExp_letter_2 = "^[A-Z]+$";
    public static final String regExp_letter_3 = "^[a-z]+$";
    public static final String regExp_letter_4 = "^[A-Za-z0-9]+$";
    public static final String regExp_letter_5 = "^\\w+$";
    public static final String regExp_chinese_1 = "[\\u4e00-\\u9fa5]";
    public static final String regExp_chinese_2 = "[^\\x00-\\xff]";
    public static final String regExp_line = "\\n[\\s ? ]*\\r";
    public static final String regExp_html = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";
    public static final String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)";
    public static final String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    public static final String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";
    public static final String regExp_qq = "[1-9][0-9]{4,}";
    public static final String regExp_postbody = "[1-9]\\d{5}(?!\\d)";
    public static final String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";
    public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";
    public static final String USER_PASSWORD = "^.{6,32}$";
    public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
    public static final String PHONE = "^1[34578]\\d{9}$";
    public static final String EMAIL_OR_PHONE = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$|^1[34578]\\d{9}$";
    public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";
    public static final String ID_CARD = "^\\d{15}$|^\\d{17}([0-9]|X)$";
    public static final String DOMAIN = "^[0-9a-zA-Z]+[0-9a-zA-Z\\.-]*\\.[a-zA-Z]{2,4}$";

    public RegexUtil() {
    }

    public static boolean match(String regex, String beTestString) {
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(beTestString);
        return matcher.matches();
    }

    public static boolean canFind(String regex, String beTestString) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beTestString);
        return matcher.find();
    }

    public static String findFirst(String regex, String beFoundString) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beFoundString);
        return matcher.find() ? matcher.group() : null;
    }

    public static String findFirstIgnoreCase(String regex, String beFoundString) {
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(beFoundString);
        return matcher.find() ? matcher.group() : null;
    }

    public static String findFirst(String regex, String beFoundString, int groupIndex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beFoundString);
        return matcher.find() ? matcher.group(groupIndex) : null;
    }

    public static String findFirstIgnoreCase(String regex, String beFoundString, int groupIndex) {
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(beFoundString);
        return matcher.find() ? matcher.group(groupIndex) : null;
    }

    public static List<String> findAll(String regex, String beFoundString) {
        List<String> l = new ArrayList();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(beFoundString);

        while(m.find()) {
            l.add(m.group());
        }

        return l;
    }

    public static List<String> findAllIgnoreCase(String regex, String beFoundString) {
        List<String> l = new ArrayList();
        Pattern p = Pattern.compile(regex, 2);
        Matcher m = p.matcher(beFoundString);

        while(m.find()) {
            l.add(m.group());
        }

        return l;
    }

    public static List<String> findAll(String regex, String beFoundString, int groupIndex) {
        List<String> l = new ArrayList();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(beFoundString);

        while(m.find()) {
            l.add(m.group(groupIndex));
        }

        return l;
    }

    public static List<String> findAllIgnoreCase(String regex, String beFoundString, int groupIndex) {
        List<String> l = new ArrayList();
        Pattern p = Pattern.compile(regex, 2);
        Matcher m = p.matcher(beFoundString);

        while(m.find()) {
            l.add(m.group(groupIndex));
        }

        return l;
    }

    public static String encodePhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return "";
        } else if (match("^1[34578]\\d{9}$", phone)) {
            String begin = phone.substring(0, 3);
            String end = phone.substring(7, phone.length());
            return begin + "****" + end;
        } else {
            return phone;
        }
    }
}
