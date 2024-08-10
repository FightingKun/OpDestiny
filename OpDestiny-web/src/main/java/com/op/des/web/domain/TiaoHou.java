package com.op.des.web.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.vo.BaZiPaiPanPageVO;
import com.op.des.web.vo.PaiPanInfoVO;
import com.op.des.web.vo.PaiPanZhuInfo;

import java.util.*;

public class TiaoHou {
    private static Map<String, Integer> tainGanTbale = new HashMap() {

        {
            put("甲", 3);
            put("乙", 1);
            put("丙", 6);
            put("丁", 4);
            put("戊", 5);
            put("己", -4);
            put("庚", -1);
            put("辛", -3);
            put("壬", -5);
            put("癸", -6);
        }
    };

    private static Map<String, Integer> diZhiTable = new HashMap() {
        {
            put("寅", 3);
            put("卯", 1);
            put("辰", -4);
            put("巳", 5);
            put("午", 6);
            put("未", 3);
            put("申", -2);
            put("酉", -3);
            put("戌", 4);
            put("亥", -5);
            put("子", -6);
            put("丑", -4);
        }
    };
    private static Map<String, Integer> yueLingTable = new HashMap() {
        {
            put("寅", 0);
            put("卯", 1);
            put("辰", 2);
            put("巳", 3);
            put("午", 4);
            put("未", 3);
            put("申", 0);
            put("酉", -1);
            put("戌", -2);
            put("亥", -3);
            put("子", -4);
            put("丑", -4);
        }
    };

    private static Map<String, String> yongSehnTable = new HashMap() {
        {
            put("甲寅", "丙癸");
            put("甲卯", "庚丙戌丁己");
            put("甲辰", "庚丁壬");
            put("甲巳", "癸丁庚");
            put("甲午", "癸丁庚");
            put("甲未", "癸丁庚");
            put("甲申", "庚丁壬");
            put("甲酉", "庚丁丙");
            put("甲戌", "庚甲壬丁癸");
            put("甲亥", "庚丁戊丙");
            put("甲子", "丁庚丙");
            put("甲丑", "丁庚丙");

            put("乙寅", "丙癸");
            put("乙卯", "丙癸");
            put("乙辰", "丙癸戌");
            put("乙巳", "癸");
            put("乙午", "癸丙");
            put("乙未", "癸丙");
            put("乙申", "丙癸己");
            put("乙酉", "癸丙丁");
            put("乙戌", "癸辛");
            put("乙亥", "丙戊");
            put("乙子", "丙");
            put("乙丑", "丙");

            put("丙寅", "壬庚");
            put("丙卯", "壬己");
            put("丙辰", "壬申");
            put("丙巳", "壬庚癸");
            put("丙午", "壬庚");
            put("丙未", "壬庚");
            put("丙申", "壬戊");
            put("丙酉", "壬癸");
            put("丙戌", "甲壬");
            put("丙亥", "甲戊庚壬");
            put("丙子", "壬戊己");
            put("丙丑", "壬申");

            put("丁寅", "甲庚");
            put("丁卯", "庚甲");
            put("丁辰", "甲庚");
            put("丁巳", "甲庚");
            put("丁午", "壬庚癸");
            put("丁未", "甲壬庚");
            put("丁申", "甲庚丙戊");
            put("丁酉", "甲庚丙戊");
            put("丁戌", "甲庚戊");
            put("丁亥", "甲庚");
            put("丁子", "甲庚");
            put("丁丑", "甲庚");

            put("戊寅", "丙甲癸");
            put("戊卯", "丙甲癸");
            put("戊辰", "甲丙癸");
            put("戊巳", "甲丙癸");
            put("戊午", "壬甲丙");
            put("戊未", "癸丙甲");
            put("戊申", "丙癸甲");
            put("戊酉", "丙癸");
            put("戊戌", "甲丙癸");
            put("戊亥", "甲丙");
            put("戊子", "丙甲");
            put("戊丑", "丙甲");

            put("己寅", "丙庚甲");
            put("己卯", "甲癸丙");
            put("己辰", "丙癸甲");
            put("己巳", "癸丙");
            put("己午", "癸丙");
            put("己未", "癸丙");
            put("己申", "丙癸");
            put("己酉", "丙癸");
            put("己戌", "甲丙癸");
            put("己亥", "丙甲戊");
            put("己子", "丙甲戊");
            put("己丑", "丙甲戊");

            put("庚寅", "戊甲丙壬丁");
            put("庚卯", "丁甲丙庚");
            put("庚辰", "甲丁壬癸");
            put("庚巳", "壬戊丙丁");
            put("庚午", "壬癸");
            put("庚未", "丁甲");
            put("庚申", "丁甲");
            put("庚酉", "丁甲丙");
            put("庚戌", "甲壬");
            put("庚亥", "丁丙");
            put("庚子", "丁甲丙");
            put("庚丑", "丙丁甲");

            put("辛寅", "己壬庚");
            put("辛卯", "壬申");
            put("辛辰", "壬申");
            put("辛巳", "壬申癸");
            put("辛午", "壬己癸");
            put("辛未", "壬庚甲");
            put("辛申", "壬申戊");
            put("辛酉", "壬甲");
            put("辛戌", "壬甲");
            put("辛亥", "壬丙");
            put("辛子", "丙戊壬甲");
            put("辛丑", "丙壬戊己");

            put("壬寅", "庚丙戊");
            put("壬卯", "戊辛庚");
            put("壬辰", "甲庚");
            put("壬巳", "壬辛庚癸");
            put("壬午", "癸庚辛");
            put("壬未", "辛甲");
            put("壬申", "戊丁");
            put("壬酉", "甲庚");
            put("壬戌", "甲丙");
            put("壬亥", "戊丙庚");
            put("壬子", "戊丙");
            put("壬丑", "丙丁甲");

            put("癸寅", "辛丙");
            put("癸卯", "庚辛");
            put("癸辰", "丙辛甲");
            put("癸巳", "辛");
            put("癸午", "庚壬癸");
            put("癸未", "庚辛壬癸");
            put("癸申", "丁");
            put("癸酉", "辛丙");
            put("癸戌", "辛甲壬癸");
            put("癸亥", "庚辛戊丁");
            put("癸子", "丙辛");
            put("癸丑", "丙丁");
        }
    };

    public static String tiaoGou(Lunar lunar) {
        String s = "命局寒暖";
        List<String> gan = Lists.newArrayList(
                lunar.getYearGan(), lunar.getMonthGan(), lunar.getDayGan(), lunar.getTimeGan()
        );
        List<String> zhi = Lists.newArrayList(
                lunar.getYearZhi(), lunar.getMonthZhi(), lunar.getDayZhi(), lunar.getTimeZhi()
        );
        int sum = 0;
        for (String value : gan) {
            sum += tainGanTbale.get(value);
        }
        for (String value : zhi) {
            sum += diZhiTable.get(value);
        }
        sum += yueLingTable.get(lunar.getMonthZhi());
        if (sum < -6) {
            return s + "寒。 ";
        } else if (sum > 6) {
            return s + "燥。";
        }
        return s + "平和。";

    }

    public static String yongshen(Lunar lunar) {
        return lunar.getDayGan() + "日" + lunar.getMonthZhi() + "月用" + yongSehnTable.get(lunar.getDayGan() + lunar.getMonthZhi());
    }
}
