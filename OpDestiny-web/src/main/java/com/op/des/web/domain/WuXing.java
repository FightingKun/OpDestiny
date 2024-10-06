package com.op.des.web.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.vo.BaZiPaiPanPageVO;
import com.op.des.web.vo.PaiPanInfoVO;
import com.op.des.web.vo.PaiPanZhuInfo;

import java.util.*;

public class WuXing {

    private static Map<String, String> ganZhiWuXing = new HashMap() {
        {
            put("甲", "木");
            put("乙", "木");
            put("寅", "木");
            put("卯", "木");

            put("丙", "火");
            put("丁", "火");
            put("巳", "火");
            put("午", "火");

            put("庚", "金");
            put("辛", "金");
            put("申", "金");
            put("酉", "金");

            put("壬", "水");
            put("癸", "水");
            put("亥", "水");
            put("子", "水");

            put("戊", "土");
            put("己", "土");
            put("辰", "土");
            put("戌", "土");
            put("丑", "土");
            put("未", "土");
        }
    };

    private static Map<String, String> shiShenWuXing = new HashMap() {
        {
            put("比劫", "和我");
            put("官杀", "克我");
            put("财星", "我克");
            put("食伤", "我生");
            put("印星", "生我");
            put("七杀", "克我");
            put("食神", "我生");
            put("官星", "克我");
        }
    };

    public static String xiYongWuXing(String yongshen, String wuXing) {
        String s = shiShenWuXing.get(yongshen);
        if (s == null) return null;
        if (s.equals("和我")) {
            return wuXing;
        }
        if (s.equals("克我")) {
            return getWuXingKeSelf(wuXing);
        }
        if (s.equals("我克")) {
            return getWuXingKe(wuXing);
        }

        if (s.equals("生我")) {
            return getWuXingShengSelf(wuXing);
        }
        return getWuXingSheng(wuXing);
    }


    private static Map<String, String> wuXingSheng = new HashMap() {
        {
            put("木", "火");
            put("火", "土");
            put("土", "金");
            put("金", "水");
            put("水", "木");
        }
    };
    private static Map<String, String> wuXingKe = new HashMap() {
        {
            put("木", "土");
            put("土", "水");
            put("水", "火");
            put("火", "金");
            put("金", "木");
        }
    };

    public static String getWuXingByGanZhi(String ganZhi) {
        return ganZhiWuXing.get(ganZhi);
    }

    public static String getWuXingSheng(String wuXing) {
        return wuXingSheng.get(wuXing);
    }

    public static String getWuXingShengSelf(String wuXing) {
        return wuXingSheng.entrySet().stream().filter(stringStringEntry -> stringStringEntry.getValue().equals(wuXing)).findFirst().get().getKey();
    }


    public static String getWuXingKe(String wuXing) {
        return wuXingKe.get(wuXing);
    }

    public static String getWuXingKeSelf(String wuXing) {
        return wuXingKe.entrySet().stream().filter(stringStringEntry -> stringStringEntry.getValue().equals(wuXing)).findFirst().get().getKey();
    }

    public static boolean deLing(Lunar lunar) {
        String dayGan = lunar.getDayGan();
        String monthZhi = lunar.getMonthZhi();
        EightChar eightChar = lunar.getEightChar();
        List<String> monthHideGan = eightChar.getMonthHideGan();
        if (Objects.equals(ganZhiWuXing.get(dayGan), ganZhiWuXing.get(monthZhi))) {
            return true;
        }
        for (String s : monthHideGan) {
            if (Objects.equals(ganZhiWuXing.get(dayGan), ganZhiWuXing.get(s))) {
                return true;
            }
        }
        return false;
    }

    public static boolean deShi(Lunar lunar, BaZiPaiPanPageVO baZiPaiPanPageVO) {
        String dayGan = lunar.getDayGan();
        String dayGanWuXing = ganZhiWuXing.get(dayGan);
        String ganZhi = lunar.getYearGan() + lunar.getMonthGan() + lunar.getTimeGan() + lunar.getDayZhi()
                + lunar.getDayZhi() + lunar.getYearZhi() + lunar.getMonthZhi() + lunar.getTimeZhi();
        int count = 0;
        for (int i = 0; i < ganZhi.length(); i++) {
            if (Objects.equals(dayGanWuXing, wuXingSheng.get(ganZhi.charAt(i) + ""))) {
                count++;
            }
        }
        if (count >= 4) {
            return true;
        }
        count = 0;
        String wuXingShengSelf = getWuXingShengSelf(dayGanWuXing);
        for (int i = 0; i < ganZhi.length(); i++) {
            if (Objects.equals(wuXingShengSelf, wuXingSheng.get(ganZhi.charAt(i) + ""))) {
                count++;
            }
        }
        if (count >= 4) {
            return true;
        }

        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        ArrayList<PaiPanZhuInfo> paiPanZhuInfos = Lists.newArrayList(paiPanInfoVO.getYearZhu(), paiPanInfoVO.getMonthZhu(), paiPanInfoVO.getDayZhu(), paiPanInfoVO.getTimeZhu());
        for (PaiPanZhuInfo paiPanZhuInfo : paiPanZhuInfos) {
            List<String> ganZhiRelations = paiPanZhuInfo.getGanZhiRelations();
            String sanhe = "三合" + dayGanWuXing;
            if (ganZhiRelations.contains(sanhe)) {
                return true;
            }
            String sanhhui = "三会" + dayGanWuXing;
            if (ganZhiRelations.contains(sanhhui)) {
                return true;
            }
        }
        return false;
    }

    public static boolean deDi(BaZiPaiPanPageVO baZiPaiPanPageVO) {
        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        ArrayList<PaiPanZhuInfo> paiPanZhuInfos = Lists.newArrayList(paiPanInfoVO.getYearZhu(), paiPanInfoVO.getMonthZhu(), paiPanInfoVO.getDayZhu(), paiPanInfoVO.getTimeZhu());
        Set<String> deDiStat = Sets.newHashSet("冠带", "临官", "帝旺");
        for (PaiPanZhuInfo paiPanZhuInfo : paiPanZhuInfos) {
            String shiErGongWei = paiPanZhuInfo.getShiErGongWei();
            if (deDiStat.contains(shiErGongWei)) {
                return true;
            }
        }
        return false;
    }


}
