package com.op.des.web;

import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.JieQi;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.Solar;
import com.op.des.web.lunar.eightchar.*;
import com.op.des.web.lunar.util.SolarUtil;
import com.op.des.web.param.BaZiPaiPanReq;
import com.op.des.web.utils.ChineseCalendarUtils;
import com.op.des.web.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 八字论命
 */
@RestController()
@RequestMapping("/op/des/bazilunming")
public class BaZiLunMingController {

    @RequestMapping("/paipan")
    @ResponseBody
    public BaZiPaiPanPageVO paiPan(BaZiPaiPanReq req) throws ParseException {

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
        //流年
        LiuNian curLiuNian = curDaYun.getCurLiuNian(curYear);
        paiPanInfoVO.setYearLiu(getLiuYearZhu(req, age, curYear, eightChar));
        //流月
        LiuYue currentLiuYue = curLiuNian.getCurrentLiuYue();
        paiPanInfoVO.setMonthLiu(getLiuMonthZhu(req, currentLiuYue, eightChar));
        //流日
        paiPanInfoVO.setDayLiu(getLiuDayZhu(req, eightChar));

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

    private PaiPanZhuInfo getLiuDayZhu(BaZiPaiPanReq req, EightChar eightChar) {
        PaiPanZhuInfo liuDayZhu = new PaiPanZhuInfo();
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        LiuDay liuDay = new LiuDay(year, month, day);
        String ganZhi = liuDay.getGanZhi();
        String gan = ganZhi.charAt(0) + "";
        String zhi = ganZhi.charAt(1) + "";

        //流日-十神
        liuDayZhu.setName("流日");
        liuDayZhu.setShiShen(liuDay.getShiShenGan(eightChar));
        liuDayZhu.setGan(gan);
        liuDayZhu.setZhi(zhi);
        //流日-藏干
        liuDayZhu.setCangGan(liuDay.getHideGan());
        //流日-藏干-十神
        liuDayZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), liuDay.getHideGan()));
        //流日-十二宫为
        liuDayZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), zhi));
        //流日-纳因
        liuDayZhu.setNaYin(liuDay.getNaYin(zhi));
        //流日-神煞
        liuDayZhu.setShenSha(liuDay.getShenSha(req.getStringSex(), eightChar));
        //流日-日/年柱空亡
        if (liuDay.getKongWang(eightChar) != null) {
            liuDayZhu.setKongWang(liuDay.getKongWang(eightChar));
        }
        return liuDayZhu;
    }

    private PaiPanZhuInfo getLiuMonthZhu(BaZiPaiPanReq req, LiuYue currentLiuYue, EightChar eightChar) {
        PaiPanZhuInfo liuMonthZhu = new PaiPanZhuInfo();
        String ganZhi = currentLiuYue.getGanZhi();
        String gan = ganZhi.charAt(0) + "";
        String zhi = ganZhi.charAt(1) + "";
        liuMonthZhu.setGan(gan);
        liuMonthZhu.setGan(zhi);
        liuMonthZhu.setName("流月");
        //流月-十神
        liuMonthZhu.setShiShen(currentLiuYue.getShiShenGan(eightChar));
        //流月-干
        liuMonthZhu.setGan(gan);
        //流月-支
        liuMonthZhu.setZhi(zhi);
        //流月-藏干
        liuMonthZhu.setCangGan(currentLiuYue.getHideGan());
        //流月-藏干-十神
        liuMonthZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), currentLiuYue.getHideGan()));
        //流月-十二宫为
        liuMonthZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), zhi));
        //流月-纳因
        liuMonthZhu.setNaYin(currentLiuYue.getNaYin(zhi));
        //流月-神煞
        liuMonthZhu.setShenSha(currentLiuYue.getShenSha(req.getStringSex(), eightChar));
        //流月-日/年柱空亡
        if (currentLiuYue.getKongWang(eightChar) != null) {
            liuMonthZhu.setKongWang(currentLiuYue.getKongWang(eightChar));
        }
        return liuMonthZhu;
    }

    private static PaiPanZhuInfo getLiuYearZhu(BaZiPaiPanReq req, int age, int curYear, EightChar eightChar) {
        PaiPanZhuInfo liuYearZhu = new PaiPanZhuInfo();
        //获取大运
        DaYun curDaYun = eightChar.getCurDaYun(req.getSex(), age);
        //流年
        LiuNian curLiuYear = curDaYun.getCurLiuNian(curYear);
        String ganZhi = curLiuYear.getGanZhi();
        String gan = ganZhi.charAt(0) + "";
        String zhi = ganZhi.charAt(1) + "";
        liuYearZhu.setGan(gan);
        liuYearZhu.setZhi(zhi);
        liuYearZhu.setName("流年");
        //流年-十神
        liuYearZhu.setShiShen(curLiuYear.getShiShenGan(eightChar));
        //流年-干
        liuYearZhu.setGan(gan);
        //流年-支
        liuYearZhu.setZhi(zhi);
        //流年-藏干
        liuYearZhu.setCangGan(curLiuYear.getHideGan());
        //流年-藏干-十神
        liuYearZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), curLiuYear.getHideGan()));
        //流年-十二宫为
        liuYearZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), zhi));
        //流年-纳因
        liuYearZhu.setNaYin(curLiuYear.getNaYin(zhi));
        //流年-神煞
        liuYearZhu.setShenSha(curLiuYear.getShenSha(req.getStringSex(), eightChar));
        //流年-日/年柱空亡
        if (curLiuYear.getKongWang(eightChar) != null) {
            liuYearZhu.setKongWang(curLiuYear.getKongWang(eightChar));
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
        personDetailVO.setLocation("经度");
        //生肖
        personDetailVO.setShuxiang(lunar.getYearShengXiao());

        //星座
        personDetailVO.setXingzuo(lunar.getSolar().getXingZuo());
        //节气
        JieQi prevJie = lunar.getPrevJie();
        List<String> jiqis = new ArrayList<>();
        jiqis.add("生日" + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        String prevJieName = prevJie.getName();
        jiqis.add(prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        prevJieName = prevJie.getName();
        jiqis.add(prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        prevJieName = prevJie.getName();
        jiqis.add(prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        jiqis.add("生日于" + prevJieName + req.getYear() + "年" + req.getMonth() + "月" + req.getDay() + "日" + req.getHour() + "时");
        personDetailVO.setJieQis(jiqis);
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

    private Lunar getLunar(BaZiPaiPanReq req, Date date) throws ParseException {
        if (req.getTimeType() == 1) {
            return Lunar.fromDate(date);
        }
        return Solar.fromDate(date).getLunar();
    }
}
