package com.op.des.web.vo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
        if (isSanHui(yearZhu.getZhi()) == 1) {
            yearZhu.getGanZhiRelations().add("三会");
        } else if (isSanHui(yearZhu.getZhi()) == 2) {
            yearZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(monthZhu.getZhi()) == 1) {
            monthZhu.getGanZhiRelations().add("三会");
        } else if (isSanHui(monthZhu.getZhi()) == 2) {
            monthZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(dayZhu.getZhi()) == 1) {
            dayZhu.getGanZhiRelations().add("三会");
        } else if (isSanHui(dayZhu.getZhi()) == 2) {
            dayZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(timeZhu.getZhi()) == 1) {
            timeZhu.getGanZhiRelations().add("三会");
        } else if (isSanHui(timeZhu.getZhi()) == 2) {
            timeZhu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(daYun.getZhi()) == 1) {
            daYun.getGanZhiRelations().add("三会");
        } else if (isSanHui(daYun.getZhi()) == 2) {
            daYun.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(yearLiu.getZhi()) == 1) {
            yearLiu.getGanZhiRelations().add("三会");
        } else if (isSanHui(yearLiu.getZhi()) == 2) {
            yearLiu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(monthLiu.getZhi()) == 1) {
            monthLiu.getGanZhiRelations().add("三会");
        } else if (isSanHui(monthLiu.getZhi()) == 2) {
            monthLiu.getGanZhiRelations().add("半三会");
        }

        if (isSanHui(dayLiu.getZhi()) == 1) {
            dayLiu.getGanZhiRelations().add("三会");
        } else if (isSanHui(dayLiu.getZhi()) == 2) {
            dayLiu.getGanZhiRelations().add("半三会");
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
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getZhi());
        ganSets.add(monthLiu.getZhi());
        ganSets.add(dayLiu.getZhi());
        ganSets.add(daYun.getZhi());
        ganSets.add(yearZhu.getZhi());
        ganSets.add(monthZhu.getZhi());
        ganSets.add(dayZhu.getZhi());
        ganSets.add(timeZhu.getZhi());
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
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getZhi());
        ganSets.add(monthLiu.getZhi());
        ganSets.add(dayLiu.getZhi());
        ganSets.add(daYun.getZhi());
        ganSets.add(yearZhu.getZhi());
        ganSets.add(monthZhu.getZhi());
        ganSets.add(dayZhu.getZhi());
        ganSets.add(timeZhu.getZhi());
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

    private int isSanHui(String zhi) {
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getZhi());
        ganSets.add(monthLiu.getZhi());
        ganSets.add(dayLiu.getZhi());
        ganSets.add(daYun.getZhi());
        ganSets.add(yearZhu.getZhi());
        ganSets.add(monthZhu.getZhi());
        ganSets.add(dayZhu.getZhi());
        ganSets.add(timeZhu.getZhi());
        if ("卯".equals(zhi)) {
            if (ganSets.contains("寅") && ganSets.contains("辰")) {
                return 1;
            }
            return 2;
        }
        if ("午".equals(zhi)) {
            if (ganSets.contains("巳") && ganSets.contains("未")) {
                return 1;
            }
            return 2;
        }

        if ("酉".equals(zhi)) {
            if (ganSets.contains("戌") && ganSets.contains("未")) {
                return 1;
            }
            return 2;
        }
        if ("子".equals(zhi)) {
            if (ganSets.contains("丑") && ganSets.contains("亥")) {
                return 1;
            }
            return 2;
        }
        return 0;

    }

    private int isSanHe(String zhi) {
        Set<String> ganSets = new HashSet<>();
        ganSets.add(yearLiu.getZhi());
        ganSets.add(monthLiu.getZhi());
        ganSets.add(dayLiu.getZhi());
        ganSets.add(daYun.getZhi());
        ganSets.add(yearZhu.getZhi());
        ganSets.add(monthZhu.getZhi());
        ganSets.add(dayZhu.getZhi());
        ganSets.add(timeZhu.getZhi());

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
