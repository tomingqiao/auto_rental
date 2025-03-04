package com.coder.rental.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

public class PinYinUtils {
    public static String getPinYin(String str) {
        return PinyinUtil.getFirstLetter(str, "").toUpperCase();
    }
}
