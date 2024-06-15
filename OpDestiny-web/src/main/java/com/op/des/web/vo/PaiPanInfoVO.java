package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiPanInfoVO {
    PaiPanZhuInfo yearZhu;
    PaiPanZhuInfo monthZhu;
    PaiPanZhuInfo dayZhu;
    PaiPanZhuInfo timeZhu;
    PaiPanZhuInfo daYun;
    PaiPanZhuInfo yearLiu;
    PaiPanZhuInfo monthLiu;
    PaiPanZhuInfo dayLiu;


    public static final Map<String, String> CHONG = new HashMap<String, String>() {
        {
            put("子", "午");
            put("午", "子");
            put("丑", "未");
            put("未", "丑");
            put("寅", "申");
            put("申", "寅");
            put("卯", "酉");
            put("酉", "卯");
            put("辰", "戌");
            put("戌", "辰");
            put("巳", "亥");
            put("亥", "巳");
        }
    };

    public void handleWuHe() {
        if (isWuhe(yearZhu.getGan())) {
            yearZhu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(monthZhu.getGan())) {
            monthZhu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(dayZhu.getGan())) {
            dayZhu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(timeZhu.getGan())) {
            timeZhu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(daYun.getGan())) {
            daYun.getGanZhiRelations().add("五合");
        }
        if (isWuhe(yearLiu.getGan())) {
            yearLiu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(monthLiu.getGan())) {
            monthLiu.getGanZhiRelations().add("五合");
        }
        if (isWuhe(dayLiu.getGan())) {
            dayLiu.getGanZhiRelations().add("五合");
        }
    }

    public void handleLiuHe() {
        if (isLiuhe(yearZhu.getZhi())) {
            yearZhu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(monthZhu.getZhi())) {
            monthZhu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(dayZhu.getZhi())) {
            dayZhu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(timeZhu.getZhi())) {
            timeZhu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(daYun.getZhi())) {
            daYun.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(yearLiu.getZhi())) {
            yearLiu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(monthLiu.getZhi())) {
            monthLiu.getGanZhiRelations().add("六合");
        }
        if (isLiuhe(dayLiu.getZhi())) {
            dayLiu.getGanZhiRelations().add("六合");
        }
    }

    public void handleLiuHai() {
        if (isLiuHai(yearZhu.getZhi())) {
            yearZhu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(monthZhu.getZhi())) {
            monthZhu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(dayZhu.getZhi())) {
            dayZhu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(timeZhu.getZhi())) {
            timeZhu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(daYun.getZhi())) {
            daYun.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(yearLiu.getZhi())) {
            yearLiu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(monthLiu.getZhi())) {
            monthLiu.getGanZhiRelations().add("六害");
        }
        if (isLiuHai(dayLiu.getZhi())) {
            dayLiu.getGanZhiRelations().add("六害");
        }
    }


    public void handleSanHui() {
        String sanHui = isSanHui(yearZhu.getZhi());
        if (sanHui != null) {
            yearZhu.getGanZhiRelations().add(sanHui);
        }
        sanHui = isSanHui(monthZhu.getZhi());
        if (sanHui != null) {
            monthZhu.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(dayZhu.getZhi());
        if (sanHui != null) {
            dayZhu.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(timeZhu.getZhi());
        if (sanHui != null) {
            timeZhu.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(daYun.getZhi());
        if (sanHui != null) {
            daYun.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(yearLiu.getZhi());
        if (sanHui != null) {
            yearLiu.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(monthLiu.getZhi());
        if (sanHui != null) {
            monthLiu.getGanZhiRelations().add(sanHui);
        }

        sanHui = isSanHui(dayLiu.getZhi());
        if (sanHui != null) {
            dayLiu.getGanZhiRelations().add(sanHui);
        }
    }

    public void handleLiuChong() {
        String zhi = yearZhu.getZhi();
        String chong = isChong(zhi);
        if (chong != null) {
            yearZhu.getGanZhiRelations().add(chong);
        }
    }

    private String isChong(String zhi) {
        Set<String> ganSets = getGanZhis();
        String chong = CHONG.get(zhi);
        return chong != null && ganSets.contains(chong) ? "六冲" : null;
    }

    private Set<String> getGanZhis() {
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getZhi());
        ganSets.add(monthLiu.getZhi());
        ganSets.add(dayLiu.getZhi());
        ganSets.add(daYun.getZhi());
        ganSets.add(yearZhu.getZhi());
        ganSets.add(monthZhu.getZhi());
        ganSets.add(dayZhu.getZhi());
        ganSets.add(timeZhu.getZhi());
        return ganSets;
    }

    public void handleShuangHe() {
        if (yearZhu.getGanZhiRelations().contains("五合") && yearZhu.getGanZhiRelations().contains("六合")) {
            yearZhu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (monthZhu.getGanZhiRelations().contains("五合") && monthZhu.getGanZhiRelations().contains("六合")) {
            monthZhu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (dayZhu.getGanZhiRelations().contains("五合") && dayZhu.getGanZhiRelations().contains("六合")) {
            dayZhu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (timeZhu.getGanZhiRelations().contains("五合") && timeZhu.getGanZhiRelations().contains("六合")) {
            timeZhu.getGanZhiRelations().add("鸳鸯双合");
        }

        if (yearLiu.getGanZhiRelations().contains("五合") && yearLiu.getGanZhiRelations().contains("六合")) {
            yearLiu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (monthLiu.getGanZhiRelations().contains("五合") && monthLiu.getGanZhiRelations().contains("六合")) {
            monthLiu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (dayLiu.getGanZhiRelations().contains("五合") && dayLiu.getGanZhiRelations().contains("六合")) {
            dayLiu.getGanZhiRelations().add("鸳鸯双合");
        }
        if (daYun.getGanZhiRelations().contains("五合") && daYun.getGanZhiRelations().contains("六合")) {
            daYun.getGanZhiRelations().add("鸳鸯双合");
        }

    }

    public void handleSanHe() {
        if (isSanHe(yearZhu.getZhi()) == 1) {
            yearZhu.getGanZhiRelations().add("三会");
        } else if (isSanHe(yearZhu.getZhi()) == 2) {
            yearZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(monthZhu.getZhi()) == 1) {
            monthZhu.getGanZhiRelations().add("三会");
        } else if (isSanHe(monthZhu.getZhi()) == 2) {
            monthZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(dayZhu.getZhi()) == 1) {
            dayZhu.getGanZhiRelations().add("三会");
        } else if (isSanHe(dayZhu.getZhi()) == 2) {
            dayZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(timeZhu.getZhi()) == 1) {
            timeZhu.getGanZhiRelations().add("三会");
        } else if (isSanHe(timeZhu.getZhi()) == 2) {
            timeZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(daYun.getZhi()) == 1) {
            daYun.getGanZhiRelations().add("三会");
        } else if (isSanHe(daYun.getZhi()) == 2) {
            daYun.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(yearLiu.getZhi()) == 1) {
            yearLiu.getGanZhiRelations().add("三会");
        } else if (isSanHe(yearLiu.getZhi()) == 2) {
            yearLiu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(monthLiu.getZhi()) == 1) {
            monthLiu.getGanZhiRelations().add("三会");
        } else if (isSanHe(monthLiu.getZhi()) == 2) {
            monthLiu.getGanZhiRelations().add("半三会");
        }

        if (isSanHe(dayLiu.getZhi()) == 1) {
            dayLiu.getGanZhiRelations().add("三会");
        } else if (isSanHe(dayLiu.getZhi()) == 2) {
            dayLiu.getGanZhiRelations().add("半三会");
        }

    }

    private boolean isWuhe(String gan) {
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getGan());
        ganSets.add(monthLiu.getGan());
        ganSets.add(dayLiu.getGan());
        ganSets.add(daYun.getGan());
        ganSets.add(yearZhu.getGan());
        ganSets.add(monthZhu.getGan());
        ganSets.add(dayZhu.getGan());
        ganSets.add(timeZhu.getGan());
        if ("甲".equals(gan) && ganSets.contains("己")) {
            return true;
        }
        if ("己".equals(gan) && ganSets.contains("甲")) {
            return true;
        }
        if ("乙".equals(gan) && ganSets.contains("庚")) {
            return true;
        }
        if ("庚".equals(gan) && ganSets.contains("乙")) {
            return true;
        }
        if ("丙".equals(gan) && ganSets.contains("辛")) {
            return true;
        }
        if ("辛".equals(gan) && ganSets.contains("丙")) {
            return true;
        }
        if ("丁".equals(gan) && ganSets.contains("壬")) {
            return true;
        }
        if ("壬".equals(gan) && ganSets.contains("丁")) {
            return true;
        }
        if ("戊".equals(gan) && ganSets.contains("癸")) {
            return true;
        }
        if ("癸".equals(gan) && ganSets.contains("戊")) {
            return true;
        }
        return false;

    }

    private boolean isLiuhe(String zhi) {
        Set<String> ganSets = getGanZhis();
        if ("子".equals(zhi) && ganSets.contains("丑")) {
            return true;
        }
        if ("丑".equals(zhi) && ganSets.contains("子")) {
            return true;
        }
        if ("寅".equals(zhi) && ganSets.contains("亥")) {
            return true;
        }
        if ("亥".equals(zhi) && ganSets.contains("寅")) {
            return true;
        }
        if ("卯".equals(zhi) && ganSets.contains("戌")) {
            return true;
        }
        if ("戌".equals(zhi) && ganSets.contains("卯")) {
            return true;
        }
        if ("辰".equals(zhi) && ganSets.contains("酉")) {
            return true;
        }
        if ("酉".equals(zhi) && ganSets.contains("辰")) {
            return true;
        }
        if ("巳".equals(zhi) && ganSets.contains("申")) {
            return true;
        }
        if ("申".equals(zhi) && ganSets.contains("巳")) {
            return true;
        }
        if ("午".equals(zhi) && ganSets.contains("未")) {
            return true;
        }
        if ("未".equals(zhi) && ganSets.contains("午")) {
            return true;
        }
        return false;

    }

    private boolean isLiuHai(String zhi) {
        Set<String> ganSets = getGanZhis();
        if ("子".equals(zhi) && ganSets.contains("未")) {
            return true;
        }
        if ("未".equals(zhi) && ganSets.contains("子")) {
            return true;
        }

        if ("丑".equals(zhi) && ganSets.contains("午")) {
            return true;
        }
        if ("午".equals(zhi) && ganSets.contains("丑")) {
            return true;
        }
        if ("寅".equals(zhi) && ganSets.contains("巳")) {
            return true;
        }
        if ("巳".equals(zhi) && ganSets.contains("寅")) {
            return true;
        }
        if ("卯".equals(zhi) && ganSets.contains("辰")) {
            return true;
        }
        if ("辰".equals(zhi) && ganSets.contains("卯")) {
            return true;
        }
        if ("申".equals(zhi) && ganSets.contains("亥")) {
            return true;
        }
        if ("亥".equals(zhi) && ganSets.contains("申")) {
            return true;
        }
        if ("酉".equals(zhi) && ganSets.contains("戌")) {
            return true;
        }
        if ("戌".equals(zhi) && ganSets.contains("酉")) {
            return true;
        }
        return false;

    }

    private String isSanHui(String zhi) {
        Set<String> ganSets = getGanZhis();
        if ("卯".equals(zhi) || "寅".equals(zhi) || "辰".equals(zhi)) {
            if (ganSets.contains("寅") && ganSets.contains("辰") && ganSets.contains("卯")) {
                return "三会木";
            }
            if ("卯".equals(zhi)) {
                return "半三会";
            }
        }

        if ("午".equals(zhi) || "巳".equals(zhi) || "未".equals(zhi)) {
            if (ganSets.contains("午") && ganSets.contains("未") && ganSets.contains("巳")) {
                return "三会火";
            }
            if ("午".equals(zhi)) {
                return "半三会";
            }
        }

        if ("酉".equals(zhi) || "申".equals(zhi) || "戌".equals(zhi)) {
            if (ganSets.contains("酉") && ganSets.contains("申") && ganSets.contains("戌")) {
                return "三会金";
            }
            if ("酉".equals(zhi)) {
                return "半三会";
            }
        }

        if ("子".equals(zhi) || "亥".equals(zhi) || "丑".equals(zhi)) {
            if (ganSets.contains("亥") && ganSets.contains("子") && ganSets.contains("丑")) {
                return "三会水";
            }
            if ("子".equals(zhi)) {
                return "半三会";
            }
        }
        return null;

    }

    private int isSanHe(String zhi) {
        Set<String> ganSets = getGanZhis();

        if ("午".equals(zhi)) {
            if (ganSets.contains("寅") && ganSets.contains("戌")) {
                return 1;
            }
            return 2;
        }
        if ("卯".equals(zhi)) {
            if (ganSets.contains("未") && ganSets.contains("亥")) {
                return 1;
            }
            return 2;
        }

        if ("酉".equals(zhi)) {
            if (ganSets.contains("巳") && ganSets.contains("丑")) {
                return 1;
            }
            return 2;
        }
        if ("子".equals(zhi)) {
            if (ganSets.contains("申") && ganSets.contains("辰")) {
                return 1;
            }
            return 2;
        }
        return 0;

    }
}
