package com.op.des.web.lunar;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.op.des.web.lunar.eightchar.DaYun;
import com.op.des.web.lunar.eightchar.Yun;
import com.op.des.web.lunar.util.LunarUtil;
import javafx.util.Pair;

import java.util.*;

/**
 * 八字
 *
 * @author 6tail
 */
public class EightChar {

    private String sex;
    /**
     * 月支，按正月起寅排列
     */
    private static final String[] MONTH_ZHI = {"", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};
    /**
     * 长生十二神
     */
    public static final String[] CHANG_SHENG = {"长生", "沐浴", "冠带", "临官", "帝旺", "衰", "病", "死", "墓", "绝", "胎", "养"};
    /**
     * 长生十二神日干偏移值，五阳干顺推，五阴干逆推
     */
    private static final Map<String, Integer> CHANG_SHENG_OFFSET = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1;

        {
            //阳
            put("甲", 1);
            put("丙", 10);
            put("戊", 10);
            put("庚", 7);
            put("壬", 4);
            //阴
            put("乙", 6);
            put("丁", 9);
            put("己", 9);
            put("辛", 0);
            put("癸", 3);
        }
    };

    /**
     * 流派，2晚子时日柱按当天，1晚子时日柱按明天
     */
    protected int sect = 2;

    /**
     * 阴历
     */
    protected Lunar lunar;

    public EightChar(Lunar lunar) {
        this.lunar = lunar;
    }

    public static EightChar fromLunar(Lunar lunar) {
        return new EightChar(lunar);
    }

    @Override
    public String toString() {
        return getYear() + " " + getMonth() + " " + getDay() + " " + getTime();
    }

    /**
     * 获取流派
     *
     * @return 流派，2晚子时日柱按当天，1晚子时日柱按明天
     */
    public int getSect() {
        return sect;
    }

    /**
     * 设置流派
     *
     * @param sect 流派，2晚子时日柱按当天，1晚子时日柱按明天，其他值默认为2
     */
    public void setSect(int sect) {
        this.sect = (1 == sect) ? 1 : 2;
    }

    /**
     * 获取年柱
     *
     * @return 年柱
     */
    public String getYear() {
        return lunar.getYearInGanZhiExact();
    }

    /**
     * 获取年干
     *
     * @return 天干
     */
    public String getYearGan() {
        return lunar.getYearGanExact();
    }

    /**
     * 获取年支
     *
     * @return 地支
     */
    public String getYearZhi() {
        return lunar.getYearZhiExact();
    }

    /**
     * 获取年柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 天干
     */
    public List<String> getYearHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getYearZhi());
    }

    /**
     * 获取年柱五行
     *
     * @return 五行
     */
    public String getYearWuXing() {
        return LunarUtil.WU_XING_GAN.get(getYearGan()) + LunarUtil.WU_XING_ZHI.get(getYearZhi());
    }

    /**
     * 获取年柱纳音
     *
     * @return 纳音
     */
    public String getYearNaYin() {
        return LunarUtil.NAYIN.get(getYear());
    }

    /**
     * 获取年柱天干十神
     *
     * @return 十神
     */
    public String getYearShiShenGan() {
        return LunarUtil.SHI_SHEN.get(getDayGan() + getYearGan());
    }


    /**
     * 获取十神
     *
     * @return 十神
     */
    public List<String> getShiShenByGan(String dayGan, List<String> specialGans) {
        List<String> shiShens = new ArrayList<>();
        for (String gan : specialGans) {
            shiShens.add(LunarUtil.SHI_SHEN.get(dayGan + gan));
        }
        return shiShens;
    }

    public List<String> getYearShenSha() {
        String yearZhi = getYearZhi();
        List<String> shenShas = Lists.newArrayList();
        //干支神煞
        List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getDayGan() + yearZhi, Lists.newArrayList());
        List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getYearGan() + yearZhi, Lists.newArrayList());
        shenShas.addAll(dayShenShas);
        shenShas.addAll(yearShenShas);

        //天干神煞
        List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(getDayGan() + yearZhi, Lists.newArrayList());
        shenShas.addAll(tianGanShenSha);

        //日支神煞
        List<String> dayZhiShenSha = LunarUtil.dayZhiShenShaTable.getOrDefault(getDayZhi() + yearZhi, Lists.newArrayList());
        shenShas.addAll(dayZhiShenSha);

        //月支神煞
        List<String> monthZhiShenSha = LunarUtil.monthZhiShenShaTable.getOrDefault(getMonthZhi() + yearZhi, Lists.newArrayList());
        shenShas.addAll(monthZhiShenSha);
        return shenShas;
    }

    public List<String> getMonthShenSha() {

        String monthZhi = getMonthZhi();
        List<String> shenShas = Lists.newArrayList();

        //干支神煞
        List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getDayGan() + monthZhi, Lists.newArrayList());
        List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getYearGan() + monthZhi, Lists.newArrayList());
        shenShas.addAll(dayShenShas);
        shenShas.addAll(yearShenShas);

        //天干神煞
        List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(getDayGan() + getMonthZhi(), Lists.newArrayList());
        shenShas.addAll(tianGanShenSha);
        //日支神煞
        List<String> dayZhiShenSha = LunarUtil.dayZhiShenShaTable.getOrDefault(getDayZhi() + monthZhi, Lists.newArrayList());
        shenShas.addAll(dayZhiShenSha);

        //年支神煞
        List<String> yearZhiShenSha = LunarUtil.yearZhiShenShaTable.getOrDefault(getYearZhi() + monthZhi, Lists.newArrayList());
        shenShas.addAll(yearZhiShenSha);

        //其他神煞
        Pair<Boolean, String> sanQiGuiRen = isSanQiGuiRen();
        if (sanQiGuiRen.getKey()) {
            shenShas.add(sanQiGuiRen.getValue());
        }
        //天煞
        Pair<Boolean, String> tianShe = isTianShe(monthZhi);
        if (tianShe.getKey()) {
            shenShas.add(tianShe.getValue());
        }
        //元辰
        Pair<Boolean, String> yuanChen = isYuanChen(monthZhi);
        if (yuanChen.getKey()) {
            shenShas.add(yuanChen.getValue());
        }
        //四废
        Pair<Boolean, String> siFei = isSiFei();
        if (siFei.getKey()) {
            shenShas.add(siFei.getValue());
        }
        //勾绞煞
        if (isGou(monthZhi) || isJiao(monthZhi)) {
            shenShas.add("勾绞煞");
        }

        return shenShas;
    }

    public List<String> getDayShenSha() {
        String dayZhi = getDayZhi();
        List<String> shenShas = Lists.newArrayList();
        //干支神煞
        List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getDayGan() + dayZhi, Lists.newArrayList());
        List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getYearGan() + dayZhi, Lists.newArrayList());
        shenShas.addAll(dayShenShas);
        shenShas.addAll(yearShenShas);

        //日柱神煞
        List<String> dayShenSha = LunarUtil.dayZhuShenShaTable.getOrDefault(getDayGan() + dayZhi, Lists.newArrayList());
        shenShas.addAll(dayShenSha);

        //天干神煞
        List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(getDayGan() + dayZhi, Lists.newArrayList());
        shenShas.addAll(tianGanShenSha);
        //月支神煞
        List<String> monthZhiShenSha = LunarUtil.monthZhiShenShaTable.getOrDefault(getMonthZhi() + dayZhi, Lists.newArrayList());
        shenShas.addAll(monthZhiShenSha);
        //年支神煞表
        List<String> yearZhiShenSha = LunarUtil.yearZhiShenShaTable.getOrDefault(getYearZhi() + dayZhi, Lists.newArrayList());
        shenShas.addAll(yearZhiShenSha);

        //其他神煞
        //三奇贵人
        Pair<Boolean, String> sanQiGuiRen = isSanQiGuiRen();
        if (sanQiGuiRen.getKey()) {
            shenShas.add(sanQiGuiRen.getValue());
        }
        //天煞
        Pair<Boolean, String> tianShe = isTianShe(dayZhi);
        if (tianShe.getKey()) {
            shenShas.add(tianShe.getValue());
        }
        //元辰
        Pair<Boolean, String> yuanChen = isYuanChen(dayZhi);
        if (yuanChen.getKey()) {
            shenShas.add(yuanChen.getValue());
        }
        //四废
        Pair<Boolean, String> siFei = isSiFei();
        if (siFei.getKey()) {
            shenShas.add(siFei.getValue());
        }
        //勾绞煞
        if (isGou(dayZhi) || isJiao(dayZhi)) {
            shenShas.add("勾绞煞");
        }
        return shenShas;
    }

    public List<String> getTimeShenSha() {
        String timeZhi = getTimeZhi();
        //干支神煞
        List<String> shenShas = Lists.newArrayList();
        List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getDayGan() + timeZhi, Lists.newArrayList());
        List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(getYearGan() + timeZhi, Lists.newArrayList());
        shenShas.addAll(dayShenShas);
        shenShas.addAll(yearShenShas);

        //天干神煞
        List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(getDayGan() + timeZhi, Lists.newArrayList());
        shenShas.addAll(tianGanShenSha);
        //天支神煞
        List<String> dayZhiShenSha = LunarUtil.dayZhiShenShaTable.getOrDefault(getDayZhi() + timeZhi, Lists.newArrayList());
        shenShas.addAll(dayZhiShenSha);
        //月支神煞
        List<String> monthZhiShenSha = LunarUtil.monthZhiShenShaTable.getOrDefault(getMonthZhi() + timeZhi, Lists.newArrayList());
        shenShas.addAll(monthZhiShenSha);
        //年支神煞表
        List<String> yearZhiShenSha = LunarUtil.yearZhiShenShaTable.getOrDefault(getYearZhi() + timeZhi, Lists.newArrayList());
        shenShas.addAll(yearZhiShenSha);

        //其他神煞
        Pair<Boolean, String> sanQiGuiRen = isSanQiGuiRen();
        if (sanQiGuiRen.getKey()) {
            shenShas.add(sanQiGuiRen.getValue());
        }
        //天煞
        Pair<Boolean, String> tianShe = isTianShe(timeZhi);
        if (tianShe.getKey()) {
            shenShas.add(tianShe.getValue());
        }
        //元辰
        Pair<Boolean, String> yuanChen = isYuanChen(timeZhi);
        if (yuanChen.getKey()) {
            shenShas.add(yuanChen.getValue());
        }
        //四废
        Pair<Boolean, String> siFei = isSiFei();
        if (siFei.getKey()) {
            shenShas.add(siFei.getValue());
        }
        //勾绞煞
        if (isGou(timeZhi) || isJiao(timeZhi)) {
            shenShas.add("勾绞煞");
        }
        return shenShas;
    }

    /**
     * 指定支是否为元辰
     *
     * @param zhi
     * @return
     */
    public Pair<Boolean, String> isYuanChen(String zhi) {
        if (isYangNanYinNv()) {
            String yuanChenZhi = LunarUtil.getPreGanByOffset(1, getYearZhi());
            boolean equals = Objects.equals(zhi, yuanChenZhi);
            return equals ? new Pair<>(true, "元辰") : new Pair<>(false, null);
        }
        if (isYinNanYangNv()) {
            String yuanChenZhi = LunarUtil.getPreGanByOffset(-1, getYearZhi());
            return Objects.equals(zhi, yuanChenZhi) ? new Pair<>(true, "元辰") : new Pair<>(false, null);
        }
        return new Pair<>(false, null);
    }

    /**
     * 是否为金神
     *
     * @return
     */
    public Pair<Boolean, String> isJinShen() {
        HashSet<String> jinShenGanZhi = Sets.newHashSet("乙丑", "乙巳", "癸酉");
        boolean contains = jinShenGanZhi.contains(getTimeGan() + getTimeZhi());
        return contains ? new Pair<>(true, "金神") : new Pair<>(false, null);

    }

    /**
     * 阴男阳女判断
     *
     * @return
     */
    public Boolean isYangNanYinNv() {
        Set<String> yangs = Sets.newHashSet("甲", "丙", "戊", "庚", "壬");
        if (Objects.equals(sex, "男") && yangs.contains(getYearGan())) {
            return true;
        }
        Set<String> yins = Sets.newHashSet("乙", "丁", "己", "辛", "癸");
        if (Objects.equals(sex, "女") && yins.contains(getYearGan())) {
            return true;
        }
        return false;
    }


    /**
     * 是否为阴男阳女
     *
     * @return
     */
    public Boolean isYinNanYangNv() {
        Set<String> yangs = Sets.newHashSet("甲", "丙", "戊", "庚", "壬");
        if (Objects.equals(sex, "女") && yangs.contains(getYearGan())) {
            return true;
        }
        Set<String> yins = Sets.newHashSet("乙", "丁", "己", "辛", "癸");
        if (Objects.equals(sex, "男") && yins.contains(getYearGan())) {
            return true;
        }
        return false;
    }

    /**
     * 是否为三奇贵人
     *
     * @return
     */
    public Pair<Boolean, String> isSanQiGuiRen() {
        List<Set<String>> sanQiGuiRens = Lists.newArrayList(Sets.newHashSet("甲", "戊", "庚"), Sets.newHashSet("乙", "丙", "丁"), Sets.newHashSet("壬", "癸", "辛"));
        HashSet<String> riZhuGan = Sets.newHashSet(getDayGan(), getMonthGan(), getTimeGan());
        for (int i = 0; i < sanQiGuiRens.size(); i++) {
            Sets.SetView<String> difference = Sets.difference(riZhuGan, sanQiGuiRens.get(i));
            if (difference.size() == 0) {
                return new Pair<>(true, "三奇贵人");
            }
        }
        return new Pair<>(false, null);
    }

    /**
     * 是否为天赦
     *
     * @return
     */
    public Pair<Boolean, String> isTianShe(String monthZhi) {
        HashSet<String> chun = Sets.newHashSet("寅", "卯");
        HashSet<String> xia = Sets.newHashSet("巳", "午");
        HashSet<String> qiu = Sets.newHashSet("申", "酉");
        HashSet<String> dong = Sets.newHashSet("亥", "子");
        String riZhu = getDayGan() + getDayZhi();
        if (Objects.equals(riZhu, "戊寅") && chun.contains(monthZhi)) {
            return new Pair<>(true, "天赦");
        }
        if (Objects.equals(riZhu, "甲午") && xia.contains(monthZhi)) {
            return new Pair<>(true, "天赦");
        }
        if (Objects.equals(riZhu, "戊申") && qiu.contains(monthZhi)) {
            return new Pair<>(true, "天赦");
        }
        if (Objects.equals(riZhu, "甲子") && dong.contains(monthZhi)) {
            return new Pair<>(true, "天赦");
        }
        return new Pair<>(false, null);
    }

    /**
     * 是否为四废
     *
     * @return
     */
    public Pair<Boolean, String> isSiFei() {
        String monthZhi = getMonthZhi();
        HashSet<String> chun = Sets.newHashSet("寅", "卯");
        HashSet<String> xia = Sets.newHashSet("巳", "午");
        HashSet<String> qiu = Sets.newHashSet("申", "酉");
        HashSet<String> dong = Sets.newHashSet("亥", "子");
        String riZhu = getDayGan() + getDayZhi();
        if (Objects.equals(riZhu, "庚申") && chun.contains(monthZhi)) {
            return new Pair<>(true, "四废");
        }
        if (Objects.equals(riZhu, "壬子") && xia.contains(monthZhi)) {
            return new Pair<>(true, "四废");
        }
        if (Objects.equals(riZhu, "甲寅") && qiu.contains(monthZhi)) {
            return new Pair<>(true, "四废");
        }
        if (Objects.equals(riZhu, "丙午") && dong.contains(monthZhi)) {
            return new Pair<>(true, "四废");
        }
        return new Pair<>(false, null);
    }

    /**
     * 获取十二宫位
     *
     * @param dayZhu
     * @param zhi
     * @return
     */

    public String getShiErGongWei(String dayZhu, String zhi) {
        return LunarUtil.SHI_ER_GONG_WEI.get(dayZhu + zhi);
    }


    private List<String> getShiShenZhi(String zhi) {
        List<String> hideGan = LunarUtil.ZHI_HIDE_GAN.get(zhi);
        List<String> l = new ArrayList<String>(hideGan.size());
        for (String gan : hideGan) {
            l.add(LunarUtil.SHI_SHEN.get(getDayGan() + gan));
        }
        return l;
    }

    /**
     * 获取年柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 十神
     */
    public List<String> getYearShiShenZhi() {
        return getShiShenZhi(getYearZhi());
    }

    /**
     * 获取日干下标
     *
     * @return 日干下标，0-9
     */
    public int getDayGanIndex() {
        return 2 == sect ? lunar.getDayGanIndexExact2() : lunar.getDayGanIndexExact();
    }

    /**
     * 获取日支下标
     *
     * @return 日支下标，0-11
     */
    public int getDayZhiIndex() {
        return 2 == sect ? lunar.getDayZhiIndexExact2() : lunar.getDayZhiIndexExact();
    }

    private String getDiShi(int zhiIndex) {
        int index = CHANG_SHENG_OFFSET.get(getDayGan()) + (getDayGanIndex() % 2 == 0 ? zhiIndex : -zhiIndex);
        if (index >= 12) {
            index -= 12;
        }
        if (index < 0) {
            index += 12;
        }
        return CHANG_SHENG[index];
    }

    /**
     * 获取年柱地势（长生十二神）
     *
     * @return 地势
     */
    public String getYearDiShi() {
        return getDiShi(lunar.getYearZhiIndexExact());
    }

    /**
     * 获取月柱
     *
     * @return 月柱
     */
    public String getMonth() {
        return lunar.getMonthInGanZhiExact();
    }

    /**
     * 获取月干
     *
     * @return 天干
     */
    public String getMonthGan() {
        return lunar.getMonthGanExact();
    }

    /**
     * 获取月支
     *
     * @return 地支
     */
    public String getMonthZhi() {
        return lunar.getMonthZhiExact();
    }

    /**
     * 获取月柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 天干
     */
    public List<String> getMonthHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getMonthZhi());
    }

    /**
     * 获取月柱五行
     *
     * @return 五行
     */
    public String getMonthWuXing() {
        return LunarUtil.WU_XING_GAN.get(getMonthGan()) + LunarUtil.WU_XING_ZHI.get(getMonthZhi());
    }

    /**
     * 获取月柱纳音
     *
     * @return 纳音
     */
    public String getMonthNaYin() {
        return LunarUtil.NAYIN.get(getMonth());
    }

    /**
     * 获取月柱天干十神
     *
     * @return 十神
     */
    public String getMonthShiShenGan() {
        return LunarUtil.SHI_SHEN.get(getDayGan() + getMonthGan());
    }

    /**
     * 获取月柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 十神
     */
    public List<String> getMonthShiShenZhi() {
        return getShiShenZhi(getMonthZhi());
    }

    /**
     * 获取月柱地势（长生十二神）
     *
     * @return 地势
     */
    public String getMonthDiShi() {
        return getDiShi(lunar.getMonthZhiIndexExact());
    }

    /**
     * 获取日柱
     *
     * @return 日柱
     */
    public String getDay() {
        return 2 == sect ? lunar.getDayInGanZhiExact2() : lunar.getDayInGanZhiExact();
    }

    /**
     * 获取日干
     *
     * @return 天干
     */
    public String getDayGan() {
        return 2 == sect ? lunar.getDayGanExact2() : lunar.getDayGanExact();
    }

    /**
     * 获取日支
     *
     * @return 地支
     */
    public String getDayZhi() {
        return 2 == sect ? lunar.getDayZhiExact2() : lunar.getDayZhiExact();
    }

    /**
     * 获取日柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 天干
     */
    public List<String> getDayHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getDayZhi());
    }

    /**
     * 获取日柱五行
     *
     * @return 五行
     */
    public String getDayWuXing() {
        return LunarUtil.WU_XING_GAN.get(getDayGan()) + LunarUtil.WU_XING_ZHI.get(getDayZhi());
    }

    /**
     * 获取日柱纳音
     *
     * @return 纳音
     */
    public String getDayNaYin() {
        return LunarUtil.NAYIN.get(getDay());
    }

    /**
     * 获取日柱天干十神，也称日元、日干
     *
     * @return 十神
     */
    public String getDayShiShenGan() {
        return "日主";
    }

    /**
     * 获取日柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 十神
     */
    public List<String> getDayShiShenZhi() {
        return getShiShenZhi(getDayZhi());
    }

    /**
     * 获取日柱地势（长生十二神）
     *
     * @return 地势
     */
    public String getDayDiShi() {
        return getDiShi(getDayZhiIndex());
    }

    /**
     * 获取时柱
     *
     * @return 时柱
     */
    public String getTime() {
        return lunar.getTimeInGanZhi();
    }

    /**
     * 获取时干
     *
     * @return 天干
     */
    public String getTimeGan() {
        return lunar.getTimeGan();
    }

    /**
     * 获取时支
     *
     * @return 地支
     */
    public String getTimeZhi() {
        return lunar.getTimeZhi();
    }

    /**
     * 获取时柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 天干
     */
    public List<String> getTimeHideGan() {
        return LunarUtil.ZHI_HIDE_GAN.get(getTimeZhi());
    }

    /**
     * 获取时柱五行
     *
     * @return 五行
     */
    public String getTimeWuXing() {
        return LunarUtil.WU_XING_GAN.get(lunar.getTimeGan()) + LunarUtil.WU_XING_ZHI.get(lunar.getTimeZhi());
    }

    /**
     * 是否为勾
     *
     * @return
     */
    public Boolean isGou(String zhi) {
        if (isYinNanYangNv()) {
            String gou = LunarUtil.getPreGanByOffset(-3, getYearZhi());
            return Objects.equals(zhi, gou);
        }
        if (isYangNanYinNv()) {
            String gou = LunarUtil.getPreGanByOffset(3, getYearZhi());
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
    public Boolean isJiao(String zhi) {
        if (isYinNanYangNv()) {
            String jiao = LunarUtil.getPreGanByOffset(3, getYearZhi());
            return Objects.equals(zhi, jiao);
        }
        if (isYangNanYinNv()) {
            String jiao = LunarUtil.getPreGanByOffset(-3, getYearZhi());
            return Objects.equals(zhi, jiao);
        }
        return false;
    }

    /**
     * 获取时柱纳音
     *
     * @return 纳音
     */
    public String getTimeNaYin() {
        return LunarUtil.NAYIN.get(getTime());
    }

    /**
     * 获取时柱天干十神
     *
     * @return 十神
     */
    public String getTimeShiShenGan() {
        return LunarUtil.SHI_SHEN.get(getDayGan() + getTimeGan());
    }

    /**
     * 获取时柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     *
     * @return 十神
     */
    public List<String> getTimeShiShenZhi() {
        return getShiShenZhi(getTimeZhi());
    }

    /**
     * 获取时柱地势（长生十二神）
     *
     * @return 地势
     */
    public String getTimeDiShi() {
        return getDiShi(lunar.getTimeZhiIndex());
    }

    /**
     * 获取胎元
     *
     * @return 胎元
     */
    public String getTaiYuan() {
        int ganIndex = lunar.getMonthGanIndexExact() + 1;
        if (ganIndex >= 10) {
            ganIndex -= 10;
        }
        int zhiIndex = lunar.getMonthZhiIndexExact() + 3;
        if (zhiIndex >= 12) {
            zhiIndex -= 12;
        }
        return LunarUtil.GAN[ganIndex + 1] + LunarUtil.ZHI[zhiIndex + 1];
    }

    /**
     * 获取胎元纳音
     *
     * @return 纳音
     */
    public String getTaiYuanNaYin() {
        return LunarUtil.NAYIN.get(getTaiYuan());
    }

    /**
     * 获取胎息
     *
     * @return 胎息
     */
    public String getTaiXi() {
        int ganIndex = (2 == sect) ? lunar.getDayGanIndexExact2() : lunar.getDayGanIndexExact();
        int zhiIndex = (2 == sect) ? lunar.getDayZhiIndexExact2() : lunar.getDayZhiIndexExact();
        return LunarUtil.HE_GAN_5[ganIndex] + LunarUtil.HE_ZHI_6[zhiIndex];
    }

    /**
     * 获取胎息纳音
     *
     * @return 纳音
     */
    public String getTaiXiNaYin() {
        return LunarUtil.NAYIN.get(getTaiXi());
    }

    /**
     * 获取命宫
     *
     * @return 命宫
     */
    public String getMingGong() {
        int monthZhiIndex = LunarUtil.find(getMonthZhi(), MONTH_ZHI, 0);
        int timeZhiIndex = LunarUtil.find(getTimeZhi(), MONTH_ZHI, 0);
        int offset = monthZhiIndex + timeZhiIndex;
        offset = (offset >= 14 ? 26 : 14) - offset;
        int ganIndex = (lunar.getYearGanIndexExact() + 1) * 2 + offset;
        while (ganIndex > 10) {
            ganIndex -= 10;
        }
        return LunarUtil.GAN[ganIndex] + MONTH_ZHI[offset];
    }

    /**
     * 获取命宫纳音
     *
     * @return 纳音
     */
    public String getMingGongNaYin() {
        return LunarUtil.NAYIN.get(getMingGong());
    }

    /**
     * 获取身宫
     *
     * @return 身宫
     */
    public String getShenGong() {
        int monthZhiIndex = LunarUtil.find(getMonthZhi(), MONTH_ZHI, 0);
        int timeZhiIndex = LunarUtil.find(getTimeZhi(), LunarUtil.ZHI, 0);
        int offset = monthZhiIndex + timeZhiIndex;
        while (offset > 12) {
            offset -= 12;
        }
        int ganIndex = (lunar.getYearGanIndexExact() + 1) * 2 + (offset % 12);
        while (ganIndex > 10) {
            ganIndex -= 10;
        }
        return LunarUtil.GAN[ganIndex] + MONTH_ZHI[offset];
    }

    /**
     * 获取身宫纳音
     *
     * @return 纳音
     */
    public String getShenGongNaYin() {
        return LunarUtil.NAYIN.get(getShenGong());
    }

    public Lunar getLunar() {
        return lunar;
    }

    /**
     * 使用默认流派1获取运
     *
     * @param gender 性别：1男，0女
     * @return 运
     */
    public Yun getYun(int gender) {
        return getYun(gender, 1);
    }

    public DaYun getCurDaYun(int gender, int age) {
        Yun yun = getYun(gender);
        DaYun[] daYun = yun.getDaYun();
        for (int i = 0; i < daYun.length; i++) {
            if (age < daYun[i].getEndAge()) {
                return daYun[i];
            }
        }
        return daYun[0];
    }

    /**
     * 获取运
     *
     * @param gender 性别：1男，0女
     * @param sect   流派，1按天数和时辰数计算，3天1年，1天4个月，1时辰10天；2按分钟数计算
     * @return 运
     */
    public Yun getYun(int gender, int sect) {
        return new Yun(this, gender, sect);
    }

    /**
     * 获取年柱所在旬
     *
     * @return 旬
     */
    public String getYearXun() {
        return lunar.getYearXunExact();
    }

    /**
     * 年空亡
     *
     * @return
     */
    public String yearKongWang() {
        String dayXunKong = getDayXunKong();
        String yearZhi = getYearZhi();
        if (dayXunKong.contains(yearZhi)) {
            return "日柱空亡";
        }
        return null;
    }


    public String monthKongWang() {
        String dayXunKong = getDayXunKong();
        String monthZhi = getMonthZhi();
        if (dayXunKong.contains(monthZhi)) {
            return "日柱空亡";
        }
        String yearXunKong = getYearXunKong();
        if (yearXunKong.contains(monthZhi)) {
            return "年柱空亡";
        }
        return null;
    }

    public String dayKongWang() {
        String dayZhi = getDayZhi();
        String yearXunKong = getYearXunKong();
        if (yearXunKong.contains(dayZhi)) {
            return "年柱空亡";
        }
        return null;
    }

    public String timeKongWang() {
        String dayXunKong = getDayXunKong();
        String timeZhi = getTimeZhi();
        if (dayXunKong.contains(timeZhi)) {
            return "日柱空亡";
        }
        String yearXunKong = getYearXunKong();
        if (yearXunKong.contains(timeZhi)) {
            return "年柱空亡";
        }
        return null;
    }

    /**
     * 获取年柱旬空(空亡)
     *
     * @return 旬空(空亡)
     */
    public String getYearXunKong() {
        return lunar.getYearXunKongExact();
    }

    /**
     * 获取月柱所在旬
     *
     * @return 旬
     */
    public String getMonthXun() {
        return lunar.getMonthXunExact();
    }

    /**
     * 获取月柱旬空(空亡)
     *
     * @return 旬空(空亡)
     */
    public String getMonthXunKong() {
        return lunar.getMonthXunKongExact();
    }

    /**
     * 获取日柱所在旬
     *
     * @return 旬
     */
    public String getDayXun() {
        return 2 == sect ? lunar.getDayXunExact2() : lunar.getDayXunExact();
    }

    /**
     * 获取日柱旬空(空亡)
     *
     * @return 旬空(空亡)
     */
    public String getDayXunKong() {
        return 2 == sect ? lunar.getDayXunKongExact2() : lunar.getDayXunKongExact();
    }

    /**
     * 获取时柱所在旬
     *
     * @return 旬
     */
    public String getTimeXun() {
        return lunar.getTimeXun();
    }

    /**
     * 获取时柱旬空(空亡)
     *
     * @return 旬空(空亡)
     */
    public String getTimeXunKong() {
        return lunar.getTimeXunKong();
    }
}
