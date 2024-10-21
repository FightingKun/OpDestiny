package com.op.des.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.op.des.web.dao.mapper.BaZiTiYaoPOMapper;
import com.op.des.web.dao.mapper.ChengGuSuanMingPOMapper;
import com.op.des.web.dao.po.BaZiTiYaoPO;
import com.op.des.web.dao.po.BaZiTiYaoPOCriteria;
import com.op.des.web.dao.po.ChengGuSuanMingPO;
import com.op.des.web.dao.po.ChengGuSuanMingPOCriteria;
import com.op.des.web.domain.*;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.JieQi;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.Solar;
import com.op.des.web.lunar.eightchar.*;
import com.op.des.web.lunar.util.SolarUtil;
import com.op.des.web.param.BaZiPaiPanReq;
import com.op.des.web.utils.ChineseCalendarUtils;
import com.op.des.web.vo.*;
import com.op.des.web.vo.userinfo.OpBaseInfoVO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

/**
 * 八字论命
 */
@RestController()
@RequestMapping("/op/des/bazilunming")
public class BaZiLunMingController {
    @Autowired
    ChengGuSuanMingPOMapper chengGuSuanMingPOMapper;
    @Autowired
    BaZiTiYaoPOMapper baZiTiYaoPOMapper;

    @RequestMapping("/baseinfo")
    @ResponseBody
    public OpBaseInfoVO baseInfo(BaZiPaiPanReq req) {
        OpBaseInfoVO opBaseInfoVO = new OpBaseInfoVO();

        //计算命主出生时间，真太阳时转换
        long timeMill = calculateTime(req);
        Date date = new Date(timeMill);
        Lunar lunar = getLunar(req, date);
        //日干
        String dayGan = lunar.getDayGan();
        String monthZhi = lunar.getMonthZhi();
        String dayGanWuXing = WuXing.getWuXingByGanZhi(dayGan);
        String monthZhiWuXing = WuXing.getWuXingByGanZhi(monthZhi);
        String wuXingSheng = WuXing.getWuXingSheng(monthZhiWuXing);
        String wuXingShengSelf = WuXing.getWuXingShengSelf(monthZhiWuXing);
        String wuXingKe = WuXing.getWuXingKe(monthZhiWuXing);
        String wuXingKeSelf = WuXing.getWuXingKeSelf(monthZhiWuXing);

        //五行强弱
        List<String> wuXing = new ArrayList<>();
        wuXing.add(dayGan + dayGanWuXing + "生于" + monthZhi + "月，" + monthZhiWuXing + "旺" + wuXingSheng + "相" + wuXingShengSelf + "休" + wuXingKeSelf + "囚" + wuXingKe + "死");
        wuXing.add(statisticsWuXing(lunar));
        BaZiPaiPanPageVO baZiPaiPanPageVO = paiPan(req);
        String qiangRuo = qiangRuo(lunar, baZiPaiPanPageVO);
        wuXing.add(qiangRuo);
        opBaseInfoVO.setWuXing(wuXing);

        //调侯
        List<String> tiaoGou = new ArrayList<>();
        tiaoGou.add(TiaoHou.tiaoGou(lunar) + TiaoHou.yongshen(lunar));
        opBaseInfoVO.setTiaoHou(tiaoGou);

        //八字格局
        List<String> baZiGeJu = new ArrayList<>();
        //正八格
        String zhengBaGe = getZhengBaGe(baZiPaiPanPageVO);
        if (zhengBaGe != null) {
            baZiGeJu.add("正八格：" + zhengBaGe);
        }
        String zhuanWangGe = getZhuanWangGe(baZiPaiPanPageVO);
        if (zhuanWangGe != null) {
            baZiGeJu.add("专旺格：" + zhuanWangGe);
        }
        List<String> congWangGe = getCongWangGe(baZiPaiPanPageVO);
        if ((wuXing.contains("命主日元弱") || wuXing.contains("命主日元很弱")) && !CollectionUtils.isEmpty(congWangGe)) {
            baZiGeJu.add("从旺格：" + congWangGe);
        }
        if (getKuiGangGe(baZiPaiPanPageVO) != null) {
            baZiGeJu.add("魁罡格");
        }
        opBaseInfoVO.setGeJu(baZiGeJu);
        //八字提要
        EightChar eightChar = lunar.getEightChar();
        String birthYue = eightChar.getMonthZhi() + "月";
        String birthDay = eightChar.getDayGan() + "日" + birthYue;
        String birthTime = eightChar.getTimeGan() + eightChar.getTimeZhi() + "时";
        BaZiTiYaoPOCriteria example = new BaZiTiYaoPOCriteria();
        BaZiTiYaoPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andZhiDayEqualTo(birthDay);
        criteria.andZhiMonthEqualTo(birthYue);
        criteria.andZhiTimeEqualTo(birthTime);
        List<BaZiTiYaoPO> baZiTiYaoPOS = baZiTiYaoPOMapper.selectByExample(example);
        BaZiTiYaoPO baZiTiYaoPO = baZiTiYaoPOS.get(0);
        String birth = dayGan + "日" + eightChar.getMonthZhi() + "月" + eightChar.getTime() + "时，";
        opBaseInfoVO.setTiYao(Lists.newArrayList(birth + baZiTiYaoPO.getContent()));
        //称骨论命
        String guWeight = ChengGuLunMing.getGuWeight(lunar);
        ChengGuSuanMingPOCriteria chengGuSuanMingPOCriteria = new ChengGuSuanMingPOCriteria();
        ChengGuSuanMingPOCriteria.Criteria chengGuSuanMingPOCriteriaCriteria = chengGuSuanMingPOCriteria.createCriteria();
        chengGuSuanMingPOCriteriaCriteria.andGuWeightEqualTo(guWeight);
        chengGuSuanMingPOCriteriaCriteria.andSexEqualTo((byte) req.getSex());
        List<ChengGuSuanMingPO> chengGuSuanMingPOS = chengGuSuanMingPOMapper.selectByExample(chengGuSuanMingPOCriteria);
        ChengGuSuanMingPO chengGuSuanMingPO = chengGuSuanMingPOS.get(0);
        String comment = chengGuSuanMingPO.getComment();
        String mingDetail = chengGuSuanMingPO.getMingDetail();
        opBaseInfoVO.setChenGu(Lists.newArrayList(mingDetail, comment));
        if (zhengBaGe == null) {
            return opBaseInfoVO;
        }
        //喜忌建议
        String dayWuXing = eightChar.getDayWuXing();
        Pair<XiJiJianYiBaseInfo, String> xiJiJianYi = getXiJiJianYi(zhengBaGe, qiangRuo, baZiPaiPanPageVO.getPaiPanInfoVO(), dayWuXing);
        if (xiJiJianYi == null) {
            return opBaseInfoVO;
        }
        opBaseInfoVO.setXiJi(Lists.newArrayList(xiJiJianYi.getValue()));
        //吉运建议
        ArrayList<String> jiYuns = Lists.newArrayList();
        String wuxing = xiJiJianYi.getKey().getXiYongWuxing().get(0);
        BaGuaWuXing.Gua baGua = BaGuaWuXing.getBaGua(wuxing);
        String join = String.join("、", baGua.getZhaiGua().getZhaiName());
        jiYuns.add("对命主有利的住在是" + baGua.getZhaiGua().getZhaiName() + "中的" + join);
        join = String.join("、", baGua.getJiuXing());
        jiYuns.add("有利的九星是" + join);
        join = String.join("、", baGua.getColors());
        jiYuns.add("有利的颜色是" + join);
        join = String.join("、", baGua.getLocations());
        jiYuns.add("有利的方位是" + join);
        join = String.join("、", baGua.getShengxiaos());
        jiYuns.add("有利的生肖是" + join);
        join = String.join("、", baGua.getNums());
        jiYuns.add("有利的数字是" + join);
        join = String.join("、", baGua.getSeasons());
        jiYuns.add("有利的季节是" + join);
        join = String.join("、", baGua.getShenShous());
        jiYuns.add("有利的神兽是" + join);
        join = String.join("、", baGua.getShapes());
        jiYuns.add("有利的形状是" + join);
        join = String.join("、", baGua.getWuYins());
        jiYuns.add("有利的五音是" + join);
        opBaseInfoVO.setJiYun(jiYuns);
        return opBaseInfoVO;

    }

    private Pair<XiJiJianYiBaseInfo, String> getXiJiJianYi(String zhengBaGe, String mingJuQiangRuo, PaiPanInfoVO paiPanInfoVO, String wuxing) {

        String qiangRuo = mingJuQiangRuo.contains("弱") ? "弱" : "强";
        String zhengGe = null;
        if (zhengBaGe.equals("正官格")) {
            zhengGe = "正官";
        }
        if (zhengBaGe.equals("七杀格")) {
            zhengGe = "七杀";
        }
        if (zhengBaGe.equals("正财格") || zhengBaGe.equals("偏财格")) {
            zhengGe = "财星";
        }
        if (zhengBaGe.equals("正印格") || zhengBaGe.equals("偏印格")) {
            zhengGe = "印星";
        }
        if (zhengBaGe.equals("食神格")) {
            zhengGe = "食神";
        }
        if (zhengBaGe.equals("伤官格")) {
            zhengGe = "伤官";
        }

        String countMingJu = countMingJu(paiPanInfoVO);
        String yongShen = ShiShen.quYongShen(zhengGe + qiangRuo + countMingJu);
        if (yongShen == null) {
            return null;
        }
        StringBuilder xijijianyi;
        XiJiJianYiBaseInfo xiJiJianYiBaseInfo = new XiJiJianYiBaseInfo();
        if (yongShen.contains("|")) {
            String[] yongShens = yongShen.split("\\|");
            xijijianyi = new StringBuilder("命局喜用");
            ArrayList<String> xiYongWuXing = Lists.newArrayList();

            for (String shen : yongShens) {
                String s = WuXing.xiYongWuXing(shen, wuxing);
                xijijianyi.append(s).append("(+").append(shen).append(")");
                xiYongWuXing.add(s);
            }
            xiJiJianYiBaseInfo.setXiYongWuxing(xiYongWuXing);

        } else {
            ArrayList<String> xiYongWuXing = Lists.newArrayList();
            xijijianyi = new StringBuilder("命局喜用");
            String s = WuXing.xiYongWuXing(yongShen, wuxing);
            xijijianyi.append(s).append("(+").append(yongShen).append(")");
            xiYongWuXing.add(s);
            xiJiJianYiBaseInfo.setXiYongWuxing(xiYongWuXing);
        }
        ArrayList<String> jiYongWuXing = Lists.newArrayList();
        if ("正官格".equals(zhengBaGe)) {
            xijijianyi.append(",").append("忌用伤官");
            String s = WuXing.xiYongWuXing(yongShen, wuxing);
            jiYongWuXing.add(s);
        } else if ("正财格".equals(zhengBaGe) || "偏财格".equals(zhengBaGe)) {
            xijijianyi.append(",").append("忌用比劫");
            String s = WuXing.xiYongWuXing(yongShen, wuxing);
            jiYongWuXing.add(s);
        } else if ("正印格".equals(zhengBaGe)) {
            xijijianyi.append(",").append("忌用财星");
            String s = WuXing.xiYongWuXing(yongShen, wuxing);
            jiYongWuXing.add(s);
        } else if ("食神格".equals(zhengBaGe)) {
            xijijianyi.append(",").append("忌用偏印");
            String s = WuXing.xiYongWuXing(yongShen, wuxing);
            jiYongWuXing.add(s);
        }
        xiJiJianYiBaseInfo.setJiYongWuXing(jiYongWuXing);
        return new Pair<>(xiJiJianYiBaseInfo, xijijianyi.toString());
    }


    private String countMingJu(PaiPanInfoVO paiPanInfoVO) {
        PaiPanZhuInfo dayZhu = paiPanInfoVO.getDayZhu();
        PaiPanZhuInfo monthZhu = paiPanInfoVO.getMonthZhu();
        PaiPanZhuInfo yearZhu = paiPanInfoVO.getYearZhu();
        PaiPanZhuInfo timeZhu = paiPanInfoVO.getTimeZhu();
        List<String> shiShens = Lists.newArrayList(monthZhu.getShiShen(), yearZhu.getShiShen(), timeZhu.getShiShen());
        shiShens.addAll(dayZhu.getCangGanShishen());
        shiShens.addAll(monthZhu.getCangGanShishen());
        shiShens.addAll(yearZhu.getCangGanShishen());
        shiShens.addAll(timeZhu.getCangGanShishen());

        Map<String, Integer> countMap = new HashMap<>();

        for (String shiShen : shiShens) {
            if (shiShen.contains("财")) {
                Integer count = countMap.getOrDefault("财星", 0);
                countMap.put("财星", count + 1);
            }
            if (shiShen.contains("食") || shiShen.contains("伤")) {
                Integer count = countMap.getOrDefault("食伤", 0);
                countMap.put("食伤", count + 1);
            }
            if (shiShen.contains("官") || shiShen.contains("杀")) {
                Integer count = countMap.getOrDefault("官杀", 0);
                countMap.put("官杀", count + 1);
            }
            if (shiShen.contains("禄") || shiShen.contains("劫")) {
                Integer count = countMap.getOrDefault("比劫", 0);
                countMap.put("比劫", count + 1);
            }

            if (shiShen.contains("印")) {
                Integer count = countMap.getOrDefault("印星", 0);
                countMap.put("印星", count + 1);
            }
        }

        Set<Map.Entry<String, Integer>> entries = countMap.entrySet();
        int maxNum = 0;
        int biJieNum = 0;
        int yinXingNUm = 0;
        String maxXing = "";
        for (Map.Entry<String, Integer> entry : entries) {
            Integer value = entry.getValue();
            if (value > maxNum) {
                maxXing = entry.getKey();
                maxNum = value;
            }
            if (entry.getKey().equals("比劫")) {
                biJieNum = value;
            }
            if (entry.getKey().equals("印星")) {
                yinXingNUm = value;
            }
        }

        if (maxNum == biJieNum && maxNum == yinXingNUm) {
            return "比劫、印星";
        }
        return maxXing;

    }

    public String getKuiGangGe(BaZiPaiPanPageVO baZiPaiPanPageVO) {
        PaiPanZhuInfo dayZhu = baZiPaiPanPageVO.getPaiPanInfoVO().getDayZhu();
        String dayGanZhi = dayZhu.getGan() + dayZhu.getZhi();
        Set<String> sets = Sets.newHashSet("庚辰", "庚戌", "壬辰", "戊戌");
        if (sets.contains(dayGanZhi)) {
            return "魁罡格";
        }
        return null;
    }

    public List<String> getCongWangGe(BaZiPaiPanPageVO baZiPaiPanPageVO) {
        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        PaiPanZhuInfo dayZhu = paiPanInfoVO.getDayZhu();
        PaiPanZhuInfo monthZhu = paiPanInfoVO.getMonthZhu();
        List<String> wuXings = Lists.newArrayList(dayZhu.getZhi(), monthZhu.getGan(), monthZhu.getZhi(), paiPanInfoVO.getYearZhu().getGan(), paiPanInfoVO.getYearZhu().getZhi(), paiPanInfoVO.getTimeZhu().getZhi(), paiPanInfoVO.getTimeZhu().getGan());

        int keSelfNum = 0;
        int needKeNum = 0;
        int needShengNum = 0;
        String wuXingByGanZhi = WuXing.getWuXingByGanZhi(dayZhu.getGan());
        for (String wuXing : wuXings) {
            if (Objects.equals(wuXing, WuXing.getWuXingKeSelf(wuXingByGanZhi))) {
                keSelfNum++;
            }
            if (Objects.equals(wuXing, WuXing.getWuXingKe(wuXingByGanZhi))) {
                needKeNum++;
            }
            if (Objects.equals(wuXing, WuXing.getWuXingSheng(wuXingByGanZhi))) {
                needShengNum++;
            }
        }
        List<String> congWangGe = Lists.newArrayList();
        if (keSelfNum >= 3) {
            congWangGe.add("从杀格");
        }
        if (needKeNum >= 3) {
            congWangGe.add("从财格");
        }
        if (needShengNum >= 3) {
            congWangGe.add("从儿格");
        }
        return congWangGe;
    }

    public String getZhengBaGe(BaZiPaiPanPageVO baZiPaiPanPageVO) {

        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        PaiPanZhuInfo monthZhu = paiPanInfoVO.getMonthZhu();
        PaiPanZhuInfo dayZhu = paiPanInfoVO.getDayZhu();
        PaiPanZhuInfo timeZhu = paiPanInfoVO.getTimeZhu();
        PaiPanZhuInfo yearZhu = paiPanInfoVO.getYearZhu();
        List<String> cangGanShishen = monthZhu.getCangGanShishen();
        List<String> tianGanShishen = Lists.newArrayList(monthZhu.getShiShen(), yearZhu.getShiShen(), timeZhu.getShiShen());
        Set<String> sets = Sets.newHashSet("甲", "丙", "庚", "壬");
        for (String cangen : cangGanShishen) {
            for (String tianGan : tianGanShishen) {
                if (Objects.equals(cangen, tianGan)) {
                    if (cangen.equals("劫财") && sets.contains(dayZhu.getGan())) {
                        return "劫刃格";
                    }
                    if (cangen.equals("比肩")) {
                        return "建禄格";
                    }
                    return cangen + "格";
                }
            }
        }
        return null;
    }

    public String getZhuanWangGe(BaZiPaiPanPageVO baZiPaiPanPageVO) {
        //日干五行
        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        PaiPanZhuInfo dayZhu = paiPanInfoVO.getDayZhu();
        String dayZhuGan = dayZhu.getGan();
        String wuXingByDayGan = WuXing.getWuXingByGanZhi(dayZhuGan);

        //月支五行
        PaiPanZhuInfo monthZhu = paiPanInfoVO.getMonthZhu();
        String monthZhuZhi = monthZhu.getZhi();
        String wuXingByMonthZhi = WuXing.getWuXingByGanZhi(monthZhuZhi);
        if (!Objects.equals(wuXingByDayGan, wuXingByMonthZhi)) {
            return null;
        }

        Set<String> ganZhiRelations = Sets.newHashSet();
        ganZhiRelations.addAll(dayZhu.getGanZhiRelations());
        ganZhiRelations.addAll(monthZhu.getGanZhiRelations());
        ganZhiRelations.addAll(paiPanInfoVO.getYearZhu().getGanZhiRelations());
        ganZhiRelations.addAll(paiPanInfoVO.getTimeZhu().getGanZhiRelations());

        if (!ganZhiRelations.contains("三合" + wuXingByDayGan) && !ganZhiRelations.contains("三会" + wuXingByDayGan)) {
            return null;
        }

        List<String> wuXings = Lists.newArrayList(dayZhu.getZhi(), monthZhu.getGan(), monthZhu.getZhi(), paiPanInfoVO.getYearZhu().getGan(), paiPanInfoVO.getYearZhu().getZhi(), paiPanInfoVO.getTimeZhu().getZhi(), paiPanInfoVO.getTimeZhu().getGan());
        for (String ganZhi : wuXings) {
            String wuXingByGanZhi = WuXing.getWuXingByGanZhi(ganZhi);
            //日柱克的
            if (Objects.equals(wuXingByGanZhi, WuXing.getWuXingKe(dayZhuGan))) {
                return null;
            }
            //克日柱的
            if (Objects.equals(wuXingByGanZhi, WuXing.getWuXingKeSelf(dayZhuGan))) {
                return null;
            }
            //日柱生的
            if (Objects.equals(wuXingByGanZhi, WuXing.getWuXingSheng(dayZhuGan))) {
                return null;
            }
        }
        Map<String, String> map = new HashMap() {
            {
                put("木", "曲直格(木)");
                put("火", "炎上格(火)");
                put("土", "稼穡格(土)");
                put("金", "从革格(金)");
                put("水", "润下格(水)");
            }
        };
        return map.get(wuXingByDayGan);
    }

    private String qiangRuo(Lunar lunar, BaZiPaiPanPageVO baZiPaiPanPageVO) {
        boolean isDeLing = WuXing.deLing(lunar);
        boolean isDeShi = WuXing.deShi(lunar, baZiPaiPanPageVO);
        boolean isDeDi = WuXing.deDi(baZiPaiPanPageVO);
        String s = "命主日元";
        if (isDeLing && isDeShi && isDeDi) {
            return s + "很强";
        }
        if (isDeLing && isDeShi) {
            return s + "强";
        }
        if (!isDeLing && !isDeShi && !isDeDi) {
            return s + "很弱";
        }
        return s + "弱";
    }

    private String statisticsWuXing(Lunar lunar) {
        StringBuilder sb = new StringBuilder("四柱五行数量，按本气计：");
        List<String> ganzhi = Lists.newArrayList(lunar.getYearGan(), lunar.getYearZhi(), lunar.getMonthGan(), lunar.getMonthZhi(), lunar.getDayGan(), lunar.getDayZhi(), lunar.getTimeGan(), lunar.getTimeZhi());
        Map<String, Integer> statisticsWuXing = new LinkedHashMap<>();
        statisticsWuXing.put("木", 0);
        statisticsWuXing.put("火", 0);
        statisticsWuXing.put("土", 0);
        statisticsWuXing.put("金", 0);
        statisticsWuXing.put("水", 0);
        statisticsWuXing(sb, ganzhi, statisticsWuXing);
        sb.append("，按余气计：");
        ganzhi.addAll(lunar.getEightChar().getYearHideGan());
        ganzhi.addAll(lunar.getEightChar().getMonthHideGan());
        ganzhi.addAll(lunar.getEightChar().getDayHideGan());
        ganzhi.addAll(lunar.getEightChar().getTimeHideGan());
        statisticsWuXing(sb, ganzhi, statisticsWuXing);
        return sb.toString();
    }

    private static void statisticsWuXing(StringBuilder sb, List<String> ganzhi, Map<String, Integer> statisticsWuXing) {
        for (int i = 0; i < ganzhi.size(); i++) {
            String wuXingByGanZhi = WuXing.getWuXingByGanZhi(ganzhi.get(i) + "");
            statisticsWuXing.put(wuXingByGanZhi, statisticsWuXing.getOrDefault(wuXingByGanZhi, 0) + 1);
        }
        Set<Map.Entry<String, Integer>> entries = statisticsWuXing.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            sb.append(entry.getKey());
            sb.append(entry.getValue());
        }
    }

    @RequestMapping("/paipan")
    @ResponseBody
    public BaZiPaiPanPageVO paiPan(BaZiPaiPanReq req) {

        BaZiPaiPanPageVO baZiPaiPanPageVO = new BaZiPaiPanPageVO();
        int age = getAge(req.getTimeType(), req.getYear());

        //计算命主出生时间，真太阳时转换
        long timeMill = calculateTime(req);
        Date date = new Date(timeMill);
        Lunar lunar = getLunar(req, date);

        //当前时间
        Solar solar = Solar.fromDate(new Date());
        int curYear = solar.getYear();
        int curMonth = solar.getMonth();

        String sex = req.getStringSex();
        //命主信息
        baZiPaiPanPageVO.setPersonDetailVO(getPersonDetailVO(req, lunar));

        //命主八字
        EightChar eightChar = lunar.getEightChar();
        PaiPanInfoVO paiPanInfoVO = getPaiPanInfoVO(req, age, curYear, sex, eightChar);
        //设置排盘结果
        baZiPaiPanPageVO.setPaiPanInfoVO(paiPanInfoVO);
        //运信息
        baZiPaiPanPageVO.setYunInfoVO(getYunInfoVO(eightChar, req.getSex(), age, curYear, curMonth));
        return baZiPaiPanPageVO;
    }

    private long calculateTime(BaZiPaiPanReq req) {
        long time = System.currentTimeMillis();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = req.getYear() + "-" + req.getMonth() + "-" + req.getDay() + " " + req.getHour() + ":00:00";
            Date date = dateFormat.parse(dateStr);
            time = date.getTime();
        } catch (Exception e) {
            //ignore
        }
        long offset = 0;
        if (req.getUseSunTime() == 1) {
            //从数据库中获取offsetTime
            //数据库获取的样例 1:24:4
            String strTime = "1:24:4";
            String[] split = strTime.split(":");
            offset = Long.parseLong(split[0]) + Long.parseLong(split[1]) * 60L + Long.parseLong(split[2]) * 60 * 60;
        }
        time += offset;
        return time;
    }

    private YunInfoVO getYunInfoVO(EightChar eightChar, int sex, int age, int curYear, int curMonth) {
        //大运
        YunInfoVO yunInfoVO = new YunInfoVO();
        Yun yun = eightChar.getYun(sex);
        DaYun[] daYuns = yun.getDaYun();
        List<YunInfoVO.DaYunItem> daYunItems = new ArrayList<>();
        for (int i = 1; i < daYuns.length; i++) {
            DaYun daYun = daYuns[i];
            YunInfoVO.DaYunItem daYunItem = new YunInfoVO.DaYunItem();
            daYunItem.setName((i + 1) + "步运");
            daYunItem.setShiShen(daYun.getShiShenGan(eightChar));
            daYunItem.setGanZhi(daYun.getGanZhi());
            daYunItem.setShiErGongWei(daYun.getShiShenGan(eightChar));
            daYunItem.setYunEndTime(daYun.getStartYear());
            daYunItem.setAge(daYun.getStartAge());
            daYunItems.add(daYunItem);
        }
        yunInfoVO.setDaYuns(daYunItems);
        //流年
        DaYun curDaYun = eightChar.getCurDaYun(sex, age);
        List<YunInfoVO.YearLiuItem> yearLius = new ArrayList<>();
        LiuNian[] liuYears = curDaYun.getLiuNian();
        for (int i = 0; i < liuYears.length; i++) {
            LiuNian liuYear = liuYears[i];
            YunInfoVO.YearLiuItem liuItem = new YunInfoVO.YearLiuItem();
            liuItem.setShiShen(liuYear.getShiShenGan(eightChar));
            liuItem.setGanZhi(liuYear.getGanZhi());
            liuItem.setYear(liuYear.getYear());
            liuItem.setAge(liuYear.getAge());
            yearLius.add(liuItem);
        }
        yunInfoVO.setYearLius(yearLius);

        //流月
        LiuYue[] liuYue = curDaYun.getCurLiuNian(curYear).getLiuYue();
        List<String> monthLius = new ArrayList<>();
        for (int i = 0; i < liuYue.length; i++) {
            monthLius.add(liuYue[i].getGanZhi());
        }
        yunInfoVO.setMonthLius(monthLius);
        //流日
        List<String> liuDays = new ArrayList<>();
        int daysOfMonth = SolarUtil.getDaysOfMonth(curYear, curMonth);
        for (int i = 0; i < daysOfMonth; i++) {
            String dayGanZhi = ChineseCalendarUtils.getDayGanZhi(curYear, curMonth, i + 1);
            liuDays.add(dayGanZhi);
        }
        yunInfoVO.setDayLius(liuDays);
        return yunInfoVO;
    }

    private PaiPanInfoVO getPaiPanInfoVO(BaZiPaiPanReq req, int age, int curYear, String sex, EightChar eightChar) {
        //八字排盘
        PaiPanInfoVO paiPanInfoVO = new PaiPanInfoVO();
        //年柱
        paiPanInfoVO.setYearZhu(yearZhu(eightChar));
        //月柱
        paiPanInfoVO.setMonthZhu(monthZhu(eightChar));
        //日柱
        paiPanInfoVO.setDayZhu(dayZhu(eightChar));
        //时柱
        paiPanInfoVO.setTimeZhu(timeZhu(eightChar));

        //获取大运
        DaYun curDaYun = eightChar.getCurDaYun(req.getSex(), age);
        paiPanInfoVO.setDaYun(getDayun(sex, curDaYun, eightChar));

        Lunar lunar = Solar.fromDate(new Date()).getLunar();
        //流年
        paiPanInfoVO.setYearLiu(getLiuYearZhu(req, lunar, eightChar));
        //流月
        paiPanInfoVO.setMonthLiu(getLiuMonthZhu(req, lunar, eightChar));
        //流日
        paiPanInfoVO.setDayLiu(getLiuDayZhu(req, lunar));

        //处理五合
        paiPanInfoVO.handleWuHe();
        //处理六合
        paiPanInfoVO.handleLiuHe();
        //处理六冲
        paiPanInfoVO.handleLiuChong();
        //处理六害
        paiPanInfoVO.handleLiuHai();
        //处理三会
        paiPanInfoVO.handleSanHui();
        //处理三合
        paiPanInfoVO.handleSanHe();
        //处理双合
        paiPanInfoVO.handleShuangHe();
        return paiPanInfoVO;
    }

    private PaiPanZhuInfo getDayun(String sex, DaYun curDaYun, EightChar eightChar) {

        PaiPanZhuInfo daYun = new PaiPanZhuInfo();
        daYun.setName("大运");
        String gan = curDaYun.getGanZhi().substring(0, 1);
        String zhi = curDaYun.getGanZhi().substring(1, 2);
        //大运-十神
        daYun.setShiShen(curDaYun.getShiShenGan(eightChar));
        //大运-干
        daYun.setGan(gan);
        //大运-支
        daYun.setZhi(zhi);
        //大运-藏干
        daYun.setCangGan(curDaYun.getHideGan());
        //大运-藏干-十神
        daYun.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), curDaYun.getHideGan()));
        //大运-十二宫为
        daYun.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), zhi));
        //大运-纳因
        daYun.setNaYin(curDaYun.getNaYin(zhi));
        //大运-神煞
        daYun.setShenSha(curDaYun.getShenSha(sex, eightChar));
        //大运-日/年柱空亡
        if (curDaYun.getKongWang(eightChar) != null) {
            daYun.setKongWang(curDaYun.getKongWang(eightChar));
        }
        return daYun;
    }

    private PaiPanZhuInfo getLiuDayZhu(BaZiPaiPanReq req,Lunar lunar) {
        PaiPanZhuInfo liuDayZhu = new PaiPanZhuInfo();
        EightChar eightChar= lunar.getEightChar();

        //流日-十神
        liuDayZhu.setName("流日");
        liuDayZhu.setShiShen(eightChar.getDayShiShenGan());
        liuDayZhu.setGan(eightChar.getDayGan());
        liuDayZhu.setZhi(eightChar.getDayZhi());
        //流日-藏干
        liuDayZhu.setCangGan(eightChar.getDayHideGan());
        //流日-藏干-十神
        liuDayZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getDayHideGan()));
        //流日-十二宫为
        liuDayZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getDayZhi()));
        //流日-纳因
        liuDayZhu.setNaYin(eightChar.getDayNaYin());
        //流日-神煞
        liuDayZhu.setShenSha(eightChar.getDayShenSha());
        //流日-日/年柱空亡
        if (eightChar.dayKongWang() != null) {
            liuDayZhu.setKongWang(eightChar.dayKongWang());
        }
        return liuDayZhu;
    }

    private PaiPanZhuInfo getLiuMonthZhu(BaZiPaiPanReq req, Lunar lunar, EightChar eightChar) {
        PaiPanZhuInfo liuMonthZhu = new PaiPanZhuInfo();
        EightChar eightChar1 = lunar.getEightChar();
        liuMonthZhu.setName("流月");
        //流月-十神
        liuMonthZhu.setShiShen(eightChar1.getMonthShiShenGan());
        //流月-干
        liuMonthZhu.setGan(eightChar1.getMonthGan());
        //流月-支
        liuMonthZhu.setZhi(eightChar1.getMonthZhi());
        //流月-藏干
        liuMonthZhu.setCangGan(eightChar1.getMonthHideGan());
        //流月-藏干-十神
        liuMonthZhu.setCangGanShishen(eightChar1.getShiShenByGan(eightChar1.getDayGan(), eightChar1.getMonthHideGan()));
        //流月-十二宫为
        liuMonthZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar1.getMonthZhi()));
        //流月-纳因
        liuMonthZhu.setNaYin(eightChar1.getMonthNaYin());
        //流月-神煞
        liuMonthZhu.setShenSha(eightChar1.getMonthShenSha());
        //流月-日/年柱空亡
        if (eightChar1.monthKongWang() != null) {
            liuMonthZhu.setKongWang(eightChar1.monthKongWang());
        }
        return liuMonthZhu;
    }

    private static PaiPanZhuInfo getLiuYearZhu(BaZiPaiPanReq req, Lunar lunar, EightChar eightChar) {
        PaiPanZhuInfo liuYearZhu = new PaiPanZhuInfo();
        //获取大运
        String yearGan = lunar.getYearGan();
        String yearZhi = lunar.getYearZhi();
        EightChar eightChar1 = lunar.getEightChar();
        //流年
        liuYearZhu.setName("流年");
        //流年-十神
        liuYearZhu.setShiShen(eightChar1.getYearShiShenGan());
        //流年-干
        liuYearZhu.setGan(yearGan);
        //流年-支
        liuYearZhu.setZhi(yearZhi);
        //流年-藏干
        liuYearZhu.setCangGan(eightChar1.getYearHideGan());
        //流年-藏干-十神
        liuYearZhu.setCangGanShishen(eightChar1.getShiShenByGan(eightChar1.getDayGan(), eightChar1.getYearHideGan()));
        //流年-十二宫为
        liuYearZhu.setShiErGongWei(eightChar1.getShiErGongWei(eightChar1.getDayGan(), yearZhi));
        //流年-纳因
        liuYearZhu.setNaYin(eightChar1.getYearNaYin());
        //流年-神煞
        liuYearZhu.setShenSha(eightChar1.getYearShenSha());
        //流年-日/年柱空亡
        if (eightChar1.yearKongWang() != null) {
            liuYearZhu.setKongWang(eightChar1.yearKongWang());
        }
        return liuYearZhu;
    }

    private static PaiPanZhuInfo monthZhu(EightChar eightChar) {
        PaiPanZhuInfo monthZhu = new PaiPanZhuInfo();
        monthZhu.setName("月柱");
        //月柱-十神
        monthZhu.setShiShen(eightChar.getMonthShiShenGan());
        //月柱-干
        monthZhu.setGan(eightChar.getMonthGan());
        //月柱-支
        monthZhu.setZhi(eightChar.getMonthZhi());
        //月柱-藏干
        monthZhu.setCangGan(eightChar.getMonthHideGan());
        //月柱-藏干-十神
        monthZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getMonthHideGan()));
        //月柱-十二宫为
        monthZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getMonthZhi()));
        //月柱-纳因
        monthZhu.setNaYin(eightChar.getMonthNaYin());
        //月柱-神煞
        monthZhu.setShenSha(eightChar.getMonthShenSha());
        //月柱-日/年柱空亡
        if (eightChar.monthKongWang() != null) {
            monthZhu.setKongWang(eightChar.monthKongWang());
        }
        return monthZhu;
    }

    private static PaiPanZhuInfo dayZhu(EightChar eightChar) {
        PaiPanZhuInfo dayZhu = new PaiPanZhuInfo();
        dayZhu.setName("日柱");
        //日柱-十神
        dayZhu.setShiShen(eightChar.getDayShiShenGan());
        //日柱-干
        dayZhu.setGan(eightChar.getDayGan());
        //日柱-支
        dayZhu.setZhi(eightChar.getDayZhi());
        //日柱-藏干
        dayZhu.setCangGan(eightChar.getDayHideGan());
        //日柱-藏干-十神
        dayZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getDayHideGan()));
        //日柱-十二宫为
        dayZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getDayZhi()));
        //日柱-纳因
        dayZhu.setNaYin(eightChar.getDayNaYin());
        //日柱-神煞
        dayZhu.setShenSha(eightChar.getDayShenSha());
        //日柱-年柱空亡
        if (eightChar.dayKongWang() != null) {
            dayZhu.setKongWang(eightChar.dayKongWang());
        }
        return dayZhu;
    }

    private static PaiPanZhuInfo timeZhu(EightChar eightChar) {
        PaiPanZhuInfo timeZhu = new PaiPanZhuInfo();
        timeZhu.setName("时柱");
        //时柱-十神
        timeZhu.setShiShen(eightChar.getTimeShiShenGan());
        //时柱-干
        timeZhu.setGan(eightChar.getTimeGan());
        //时柱-支
        timeZhu.setZhi(eightChar.getTimeZhi());
        //时柱-藏干
        timeZhu.setCangGan(eightChar.getTimeHideGan());
        //时柱-藏干-十神
        timeZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getTimeHideGan()));
        //时柱-十二宫为
        timeZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getTimeZhi()));
        //时柱-纳因
        timeZhu.setNaYin(eightChar.getTimeNaYin());
        //时柱-神煞
        timeZhu.setShenSha(eightChar.getTimeShenSha());
        //时柱-日/年柱空亡
        if (eightChar.timeKongWang() != null) {
            timeZhu.setKongWang(eightChar.timeKongWang());
        }
        return timeZhu;
    }

    private static PaiPanZhuInfo yearZhu(EightChar eightChar) {
        //年柱
        PaiPanZhuInfo yearZhu = new PaiPanZhuInfo();
        yearZhu.setName("年柱");
        //年柱-十神
        yearZhu.setShiShen(eightChar.getYearShiShenGan());
        //年柱-干
        yearZhu.setGan(eightChar.getYearGan());
        //年柱-支
        yearZhu.setZhi(eightChar.getYearZhi());
        //年柱-藏干
        yearZhu.setCangGan(eightChar.getYearHideGan());
        //年柱-藏干-十神
        yearZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getYearHideGan()));
        //年柱-十二宫为
        yearZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getYearZhi()));
        //年柱-纳因
        yearZhu.setNaYin(eightChar.getYearNaYin());
        //年柱-神煞
        yearZhu.setShenSha(eightChar.getYearShenSha());
        //年柱-日柱空亡
        if (eightChar.yearKongWang() != null) {
            yearZhu.setKongWang(eightChar.yearKongWang());
        }
        return yearZhu;
    }

    private PersonDetailVO getPersonDetailVO(BaZiPaiPanReq req, Lunar lunar) {
        //命主信息
        PersonDetailVO personDetailVO = new PersonDetailVO();
        personDetailVO.setUserName(req.getName());
        personDetailVO.setSex(req.getSex());
        personDetailVO.setLocation(req.getBirthAddressProvince() + req.getBirthAddressCity());
        //TODO
//        personDetailVO.setLocation("经度");
        //生肖
        personDetailVO.setShuxiang(lunar.getYearShengXiao());

        //星座
        personDetailVO.setXingzuo(lunar.getSolar().getXingZuo());
        //节气
        JieQi prevJie = lunar.getPrevJie();
        List<String> jiqis = new ArrayList<>();
        jiqis.add("生日" + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        String prevJieName = prevJie.getName();
        jiqis.add(prevJieName + prevJie.getSolar().getYear() + "年" + prevJie.getSolar().getMonth() + "月" + prevJie.getSolar().getDay() + "日");
        prevJie = lunar.getPrevJie();
        prevJieName = prevJie.getName();
        jiqis.add(prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        prevJie = lunar.getPrevJie();
        prevJieName = prevJie.getName();
        jiqis.add(prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        jiqis.add("生于" + prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        personDetailVO.setJieQis(jiqis);

        //四柱五行
        personDetailVO.setSiZhuWuXing(Lists.newArrayList(statisticsWuXing(lunar)));
        boolean b = WuXing.deLing(lunar);
        personDetailVO.setDeling(b ? lunar.getDayGan() + WuXing.getWuXingByDayGan(lunar.getDayGan()) + "得令" : null);
        //八字提要
        EightChar eightChar = lunar.getEightChar();
        String birthYue = eightChar.getMonthZhi() + "月";
        String birthDay = eightChar.getDayGan() + "日" + birthYue;
        String birthTime = eightChar.getTimeGan() + eightChar.getTimeZhi() + "时";
        BaZiTiYaoPOCriteria example = new BaZiTiYaoPOCriteria();
        BaZiTiYaoPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andZhiDayEqualTo(birthDay);
        criteria.andZhiMonthEqualTo(birthYue);
        criteria.andZhiTimeEqualTo(birthTime);
        List<BaZiTiYaoPO> baZiTiYaoPOS = baZiTiYaoPOMapper.selectByExample(example);
        BaZiTiYaoPO baZiTiYaoPO = baZiTiYaoPOS.get(0);
        String birth = lunar.getDayGan() + "日" + eightChar.getMonthZhi() + "月" + eightChar.getTime() + "时提要";
        personDetailVO.setBaZiTiyao(Lists.newArrayList(birth + baZiTiYaoPO.getContent()));

        return personDetailVO;
    }

    private int getAge(int timeType, int year) {
        Date date = new Date();
        if (timeType == 1) {
            Lunar lunar = Lunar.fromDate(date);
            return lunar.getYear() - year;
        }
        return Year.now().getValue() - year;
    }

    private Lunar getLunar(BaZiPaiPanReq req, Date date) {
        if (req.getTimeType() == 1) {
            return Lunar.fromDate(date);
        }
        return Solar.fromDate(date).getLunar();
    }
}
