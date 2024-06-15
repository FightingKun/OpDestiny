package com.op.des.web.utils;

import com.google.common.collect.Sets;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.util.LunarUtil;
import javafx.util.Pair;

import java.util.Objects;
import java.util.Set;

public class PaiPanUtils {
    /**
     * 是否为元辰
     *
     * @param zhi
     * @param sex
     * @return
     */
    public static Pair<Boolean, String> isYuanChen(String sex, String zhi, EightChar eightChar) {
        if (isYangNanYinNv(sex, eightChar.getYearGan())) {
            String yuanChenZhi = LunarUtil.getPreGanByOffset(1, eightChar.getYearZhi());
            boolean equals = Objects.equals(zhi, yuanChenZhi);
            return equals ? new Pair<>(true, "元辰") : new Pair<>(false, null);
        }
        if (isYinNanYangNv(sex, eightChar.getYearGan())) {
            String yuanChenZhi = LunarUtil.getPreGanByOffset(-1, eightChar.getYearZhi());
            return Objects.equals(zhi, yuanChenZhi) ? new Pair<>(true, "元辰") : new Pair<>(false, null);
        }
        return new Pair<>(false, null);
    }

    /**
     * 是否为勾绞煞
     *
     * @param zhi
     * @param sex
     * @param yearGan
     * @param yearZhi
     * @return
     */
    public static Boolean isGouJiaoSha(String zhi, String sex, String yearGan, String yearZhi) {
        return isGou(zhi, sex, yearGan, yearZhi) || isJiao(zhi, sex, yearGan, yearZhi);
    }

    /**
     * 是否为勾
     *
     * @return
     */
    public static Boolean isGou(String zhi, String sex, String yearGan, String yearZhi) {
        if (isYinNanYangNv(sex, yearGan)) {
            String gou = LunarUtil.getPreGanByOffset(-3, yearZhi);
            return Objects.equals(zhi, gou);
        }
        if (isYangNanYinNv(sex, yearGan)) {
            String gou = LunarUtil.getPreGanByOffset(3, yearZhi);
            return Objects.equals(zhi, gou);
        }
        return false;
    }

    /**
     * 是否为绞
     *
     * @param zhi
     * @return
     */
    public static Boolean isJiao(String zhi, String sex, String yearGan, String yearZhi) {
        if (isYinNanYangNv(sex, yearGan)) {
            String jiao = LunarUtil.getPreGanByOffset(3, yearZhi);
            return Objects.equals(zhi, jiao);
        }
        if (isYangNanYinNv(sex, yearGan)) {
            String jiao = LunarUtil.getPreGanByOffset(-3, yearZhi);
            return Objects.equals(zhi, jiao);
        }
        return false;
    }

    /**
     * 阴男阳女判断
     *
     * @return
     */
    public static Boolean isYangNanYinNv(String sex, String yearGan) {
        Set<String> yangs = Sets.newHashSet("甲", "丙", "戊", "庚", "壬");
        if (Objects.equals(sex, "男") && yangs.contains(yearGan)) {
            return true;
        }
        Set<String> yins = Sets.newHashSet("乙", "丁", "己", "辛", "癸");
        if (Objects.equals(sex, "女") && yins.contains(yearGan)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为阴男阳女
     *
     * @return
     */
    public static Boolean isYinNanYangNv(String sex, String yearGan) {
        Set<String> yangs = Sets.newHashSet("甲", "丙", "戊", "庚", "壬");
        if (Objects.equals(sex, "女") && yangs.contains(yearGan)) {
            return true;
        }
        Set<String> yins = Sets.newHashSet("乙", "丁", "己", "辛", "癸");
        if (Objects.equals(sex, "男") && yins.contains(yearGan)) {
            return true;
        }
        return false;
    }
}
