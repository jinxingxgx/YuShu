package com.xgx.yushu.utils;

import com.blankj.utilcode.util.StringUtils;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class UnicodeUtils {

    public static String unicode2String(String unicode) {
        if (StringUtils.isEmpty(unicode)) return null;
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static String changePhone(String phone) {
        String newPhone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return newPhone;
    }

    public static String string2Unicode(String string) {

        if (StringUtils.isEmpty(string)) return null;
        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }
}
