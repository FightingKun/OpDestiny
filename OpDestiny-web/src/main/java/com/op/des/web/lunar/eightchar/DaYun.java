package com.op.des.web.lunar.eightchar;


import com.google.common.collect.Lists;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.util.LunarUtil;
import com.op.des.web.utils.PaiPanUtils;
import javafx.util.Pair;

import java.util.List;

/**
 * 大运
 *
 * @author 6tail
 */
public class DaYun {
    /**
     * 开始年(含)
     */
    private final int startYear;

    /**
     * 结束年(含)
     */
    private final int endYear;

    /**
     * 开始年龄(含)
     */
    private final int startAge;

    /**
     * 结束年龄(含)
     */
    private final int endAge;

    /**
     * 序数，0-9
     */
    private final int index;

    /**
     * 运
     */
    private final Yun yun;

    private final Lunar lunar;

    public DaYun(Yun yun, int index) {
        this.yun = yun;
        this.lunar = yun.getLunar();
        this.index = index;
        int birthYear = lunar.getSolar().getYear();
        int year = yun.getStartSolar().getYear();
        if (index < 1) {
            this.startYear = birthYear;
            this.startAge = 1;
            this.endYear = year - 1;
            this.endAge = year - birthYear;
        } else {
            int add = (index - 1) * 10;
            this.startYear = year + add;
            this.startAge = this.startYear - birthYear + 1;
            this.endYear = this.startYear + 9;
            this.endAge = this.startAge + 9;
        }
    }


    public String getShiShenGan(EightChar eightChar) {
        return LunarUtil.SHI_SHEN.get(eightChar.getDayGan() + getGanZhi().substring(0, 1));
    }

    public List<String> getHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getGanZhi().substring(1, 2));
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

    public String getNaYin(String zhi) {
        return LunarUtil.NAYIN.get(zhi);
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getStartAge() {
        return startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public int getIndex() {
        return index;
    }

    public Lunar getLunar() {
        return lunar;
    }

    /**
     * 获取干支
     *
     * @return 干支
     */
    public String getGanZhi() {
        if (index < 1) {
            return "";
        }
        int offset = LunarUtil.getJiaZiIndex(lunar.getMonthInGanZhiExact());
        offset += yun.isForward() ? index : -index;
        int size = LunarUtil.JIA_ZI.length;
        if (offset >= size) {
            offset -= size;
        }
        if (offset < 0) {
            offset += size;
        }
        return LunarUtil.JIA_ZI[offset];
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
     * 获取10轮流年
     *
     * @return 流年
     */
    public LiuNian[] getLiuNian() {
        return getLiuNian(10);
    }

    public LiuNian getCurLiuNian(int year) {
        LiuNian[] liuNian = getLiuNian();
        for (LiuNian cur : liuNian) {
            if (cur.getYear() == year) {
                return cur;
            }
        }
        return liuNian[0];
    }

    /**
     * 获取流年
     *
     * @param n 轮数
     * @return 流年
     */
    public LiuNian[] getLiuNian(int n) {
        if (index < 1) {
            n = endYear - startYear + 1;
        }
        LiuNian[] l = new LiuNian[n];
        for (int i = 0; i < n; i++) {
            l[i] = new LiuNian(this, i);
        }
        return l;
    }

    /**
     * 获取10轮小运
     *
     * @return 小运
     */
    public XiaoYun[] getXiaoYun() {
        return getXiaoYun(10);
    }

    /**
     * 获取小运
     *
     * @param n 轮数
     * @return 小运
     */
    public XiaoYun[] getXiaoYun(int n) {
        if (index < 1) {
            n = endYear - startYear + 1;
        }
        XiaoYun[] l = new XiaoYun[n];
        for (int i = 0; i < n; i++) {
            l[i] = new XiaoYun(this, i, yun.isForward());
        }
        return l;
    }
}
