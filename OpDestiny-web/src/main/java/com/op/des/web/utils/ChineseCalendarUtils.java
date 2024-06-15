package com.op.des.web.utils;

public class ChineseCalendarUtils {

    // 日干支计算起始点
    private static final int BASE_YEAR = 1900;
    private static final int BASE_MONTH = 1;
    private static final int BASE_DAY = 31;
    private static final int BASE_INDEX = 0; // 1900-01-31是甲寅日

    // 天干
    private static final String[] HEAVENLY_STEMS = {
            "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
    };

    // 地支
    private static final String[] EARTHLY_BRANCHES = {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };

    // 计算儒略日
    public static int toJulianDay(int year, int month, int day) {
        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    }

    // 计算日干支
    public static String getDayGanZhi(int year, int month, int day) {
        int julianDay = toJulianDay(year, month, day);
        int daysSinceBaseDay = julianDay - toJulianDay(BASE_YEAR, BASE_MONTH, BASE_DAY);
        int index = (daysSinceBaseDay + BASE_INDEX) % 60;
        if (index < 0) {
            index += 60; // 转换为正数
        }
        return HEAVENLY_STEMS[index % 10] + EARTHLY_BRANCHES[index % 12];
    }
}