package com.op.des.web.lunar.eightchar;


import com.google.common.collect.Lists;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.util.LunarUtil;
import com.op.des.web.utils.PaiPanUtils;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 流年
 *
 * @author 6tail
 */
public class LiuNian {
    /**
     * 序数，0-9
     */
    private final int index;

    /**
     * 大运
     */
    private final DaYun daYun;

    /**
     * 年
     */
    private final int year;

    /**
     * 年龄
     */
    private final int age;

    private final Lunar lunar;

    public LiuNian(DaYun daYun, int index) {
        this.daYun = daYun;
        this.lunar = daYun.getLunar();
        this.index = index;
        this.year = daYun.getStartYear() + index;
        this.age = daYun.getStartAge() + index;
    }

    public int getIndex() {
        return index;
    }

    public int getYear() {
        return year;
    }

    public int getAge() {
        return age;
    }

    /**
     * 获取干支
     *
     * @return 干支
     */
    public String getGanZhi() {
        // 干支与出生日期和起运日期都没关系
        int offset = LunarUtil.getJiaZiIndex(lunar.getJieQiTable().get("立春").getLunar().getYearInGanZhiExact()) + this.index;
        if (daYun.getIndex() > 0) {
            offset += daYun.getStartAge() - 1;
        }
        offset %= LunarUtil.JIA_ZI.length;
        return LunarUtil.JIA_ZI[offset];
    }

    public String getShiShenGan(EightChar eightChar) {
        return LunarUtil.SHI_SHEN.get(eightChar.getDayGan() + getGanZhi().substring(0, 1));
    }

    public List<String> getHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getGanZhi().substring(1, 2));
    }

    public String getNaYin(String zhi) {
        return LunarUtil.NAYIN.get(zhi);
    }

    public String getKongWang(EightChar eightChar) {
        String dayXunKong = eightChar.getDayXunKong();
        String zhi = getGanZhi().substring(1,2);
        if (dayXunKong.contains(zhi)) {
            return "日柱空亡";
        }
        String yearXunKong = eightChar.getYearXunKong();
        if (yearXunKong.contains(zhi)) {
            return "年柱空亡";
        }
        return null;
    }

    public List<String> getShenSha(String sex, EightChar eightChar) {
        String gan = getGanZhi().substring(0, 1), zhi = getGanZhi().substring(1, 2);
        List<String> shenShas = Lists.newArrayList();

        //干支神煞
        List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(eightChar.getDayGan() + zhi, Lists.newArrayList());
        List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(eightChar.getYearGan() + zhi, Lists.newArrayList());
        shenShas.addAll(dayShenShas);
        shenShas.addAll(yearShenShas);

        //天干神煞
        List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(eightChar.getDayGan() + zhi, Lists.newArrayList());
        shenShas.addAll(tianGanShenSha);
        //日支神煞
        List<String> dayZhiShenSha = LunarUtil.dayZhiShenShaTable.getOrDefault(eightChar.getDayZhi() + zhi, Lists.newArrayList());
        shenShas.addAll(dayZhiShenSha);
        //月支神煞
        List<String> monthZhiShenSha = LunarUtil.monthZhiShenShaTable.getOrDefault(eightChar.getMonthZhi() + zhi, Lists.newArrayList());
        shenShas.addAll(monthZhiShenSha);
        //年支神煞表
        List<String> yearZhiShenSha = LunarUtil.yearZhiShenShaTable.getOrDefault(eightChar.getYearZhi() + zhi, Lists.newArrayList());
        shenShas.addAll(yearZhiShenSha);

        //其他神煞
        //元辰
        Pair<Boolean, String> yuanChen = PaiPanUtils.isYuanChen(sex, zhi, eightChar);
        if (yuanChen.getKey()) {
            shenShas.add(yuanChen.getValue());
        }
        //勾绞煞
        if (PaiPanUtils.isGouJiaoSha(zhi, sex, eightChar.getYearGan(), eightChar.getYearZhi())) {
            shenShas.add("勾绞煞");
        }

        return shenShas;
    }

    /**
     * 获取所在旬
     *
     * @return 旬
     */
    public String getXun() {
        return LunarUtil.getXun(getGanZhi());
    }

    /**
     * 获取旬空(空亡)
     *
     * @return 旬空(空亡)
     */
    public String getXunKong() {
        return LunarUtil.getXunKong(getGanZhi());
    }

    /**
     * 获取流月
     *
     * @return 流月
     */
    public LiuYue[] getLiuYue() {
        int n = 12;
        LiuYue[] l = new LiuYue[n];
        for (int i = 0; i < n; i++) {
            l[i] = new LiuYue(this, i);
        }
        return l;
    }


    public LiuYue getCurrentLiuYue() {
        int monthIndex = new Date().getMonth();
        return getLiuYue()[monthIndex-1];
    }
}
