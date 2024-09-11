package com.op.des.web.domain;

import com.op.des.web.lunar.Lunar;

import java.util.HashMap;
import java.util.Map;

public class ChengGuLunMing {

    private static Map<String, Integer> yearWeightTable = new HashMap() {{
        put("甲子", 12);
        put("丙子", 16);
        put("戌子", 15);
        put("庚子", 7);
        put("壬子", 5);

        put("乙丑", 9);
        put("丁丑", 8);
        put("己丑", 7);
        put("辛丑", 7);
        put("癸丑", 7);

        put("丙寅", 6);
        put("戊寅", 8);
        put("庚寅", 9);
        put("壬寅", 9);
        put("甲寅", 12);

        put("丁卯", 7);
        put("己卯", 19);
        put("辛卯", 12);
        put("癸卯", 12);
        put("乙卯", 8);

        put("戊辰", 12);
        put("庚辰", 12);
        put("壬辰", 1);
        put("甲辰", 8);
        put("丙辰", 8);

        put("己巳", 5);
        put("辛巳", 6);
        put("癸巳", 7);
        put("乙巳", 7);
        put("丁巳", 6);

        put("庚午", 9);
        put("壬午", 8);
        put("甲午", 15);
        put("丙午", 13);
        put("戊午", 19);

        put("辛未", 8);
        put("癸未", 7);
        put("乙未", 6);
        put("丁未", 5);
        put("己未", 6);

        put("壬申", 7);
        put("甲申", 5);
        put("丙申", 5);
        put("戊申", 14);
        put("庚申", 8);

        put("癸酉", 8);
        put("乙酉", 15);
        put("丁酉", 14);
        put("己酉", 5);
        put("辛酉", 16);

        put("甲戌", 15);
        put("丙戌", 6);
        put("戊戌", 14);
        put("庚戌", 9);
        put("壬戌", 1);

        put("乙亥", 9);
        put("丁亥", 16);
        put("己亥", 9);
        put("辛亥", 17);
        put("癸亥", 6);

    }};

    private static Map<String, Integer> monthWeightTable = new HashMap() {{
        put("寅", 6);
        put("卯", 7);
        put("辰", 18);
        put("巳", 9);
        put("午", 5);
        put("未", 16);
        put("申", 9);
        put("酉", 15);
        put("戌", 18);
        put("亥", 8);
        put("子", 9);
        put("丑", 5);
    }};

    private static Map<String, Integer> dayWeightTable = new HashMap() {{
        put("初一", 5);
        put("初二", 1);
        put("初三", 8);
        put("初四", 15);
        put("初五", 16);
        put("初六", 15);
        put("初七", 8);
        put("初八", 16);
        put("初九", 8);
        put("初十", 16);
        put("十一", 9);
        put("十二", 17);
        put("十三", 8);
        put("十四", 17);
        put("十五", 1);
        put("十六", 8);
        put("十七", 9);
        put("十八", 18);
        put("十九", 5);
        put("二十", 15);
        put("廿一", 1);
        put("廿二", 9);
        put("廿三", 8);
        put("廿四", 9);
        put("廿五", 15);
        put("廿六", 18);
        put("廿七", 7);
        put("廿八", 8);
        put("廿九", 16);
        put("三十", 6);
    }};

    private static Map<String, Integer> timeWeightTable = new HashMap() {{
        put("子", 5);
        put("丑", 1);
        put("寅", 8);
        put("卯", 15);
        put("辰", 15);
        put("巳", 15);
        put("午", 15);
        put("未", 15);
        put("申", 15);
        put("酉", 15);
        put("戌", 15);
        put("亥", 15);
    }};

    private static String[] arr = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九"};

    public static String getGuWeight(Lunar lunar) {
        String yearInGanZhi = lunar.getYearInGanZhi();
        String monthZhi = lunar.getMonthZhi();
        String dayInChinese = lunar.getDayInChinese();
        String timeZhi = lunar.getTimeZhi();
        int weight = yearWeightTable.get(yearInGanZhi) + monthWeightTable.get(monthZhi) + dayWeightTable.get(dayInChinese) + timeWeightTable.get(timeZhi);
        int liangs = weight / 10;
        int qians = weight % 10;
        if (qians == 0) {
            return arr[liangs - 1] + "两";
        }
        if (liangs == 0) {
            return arr[qians - 1] + "钱";
        }
        return arr[liangs - 1] + "两" + arr[qians - 1] + "钱";

    }
}
