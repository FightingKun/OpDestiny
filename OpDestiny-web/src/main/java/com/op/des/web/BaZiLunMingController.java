package com.op.des.web;

import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.JieQi;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.Solar;
import com.op.des.web.lunar.eightchar.DaYun;
import com.op.des.web.lunar.eightchar.LiuNian;
import com.op.des.web.lunar.eightchar.LiuYue;
import com.op.des.web.lunar.eightchar.Yun;
import com.op.des.web.lunar.util.LunarUtil;
import com.op.des.web.param.BaZiPaiPanReq;
import com.op.des.web.utils.ChineseCalendarUtils;
import com.op.des.web.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import java.time.Year;
import java.util.*;

/**
 * 八字论命
 */
@RestController("op/des/bazilunming")
public class BaZiLunMingController {

    @RequestMapping("/paipan")
    @ResponseBody
    public BaZiPaiPanPageVO PaiPan(BaZiPaiPanReq req) {

        BaZiPaiPanPageVO baZiPaiPanPageVO = new BaZiPaiPanPageVO();

        int age = getAge(req.getTimeType(), req.getYear());
        Lunar lunar = getLunar(req);
        Lunar curLunar = Lunar.fromDate(new Date());
        int curYear = curLunar.getYear();
        //命主信息
        baZiPaiPanPageVO.setPersonDetailVO(getPersonDetailVO(req, lunar));

        //命主八字
        EightChar eightChar = lunar.getEightChar();

        //八字排盘
        PaiPanInfoVO paiPanInfoVO = new PaiPanInfoVO();
        //年柱
        paiPanInfoVO.setYearZhu(yearZhu(eightChar));
        //月柱
        paiPanInfoVO.setMonthZhu(monthZhu(eightChar));

        //获取大运
        DaYun curDaYun = eightChar.getCurDaYun(req.getSex(), age);
        //流年
        LiuNian curLiuNian = curDaYun.getCurLiuNian(curYear);
        paiPanInfoVO.setYearLiu(getLiuYearZhu(req, age, curYear, eightChar));
        //流月
        LiuYue currentLiuYue = curLiuNian.getCurrentLiuYue();
        paiPanInfoVO.setMonthLiu(getLiuMonthZhu(currentLiuYue));
        //流日
        paiPanInfoVO.setDayLiu(getDayLiuZhu());
        //处理五合
        paiPanInfoVO.handleWuHe();
        //处理六合
        paiPanInfoVO.handleLiuHe();
        //处理六害
        paiPanInfoVO.handleLiuHai();
        //处理三会
        paiPanInfoVO.handleSanHui();
        //处理三会
        paiPanInfoVO.handleSanHe();
        return baZiPaiPanPageVO;
    }

    private PaiPanZhuInfo getDayLiuZhu() {
        PaiPanZhuInfo paiPanZhuInfo = new PaiPanZhuInfo();
        Date date = new Date();
        String dayGanZhi = ChineseCalendarUtils.getDayGanZhi(date.getYear(), date.getMonth(), date.getDay());
        paiPanZhuInfo.setGan(dayGanZhi.charAt(0) + "");
        paiPanZhuInfo.setGan(dayGanZhi.charAt(1) + "");
        return paiPanZhuInfo;
    }

    private PaiPanZhuInfo getLiuMonthZhu(LiuYue currentLiuYue) {
        PaiPanZhuInfo paiPanZhuInfo = new PaiPanZhuInfo();
        String ganZhi = currentLiuYue.getGanZhi();
        paiPanZhuInfo.setGan(ganZhi.charAt(0) + "");
        paiPanZhuInfo.setGan(ganZhi.charAt(1) + "");
        return paiPanZhuInfo;
    }

    private static PaiPanZhuInfo getLiuYearZhu(BaZiPaiPanReq req, int age, int curYear, EightChar eightChar) {
        PaiPanZhuInfo paiPanZhuInfo = new PaiPanZhuInfo();
        //获取大运
        DaYun curDaYun = eightChar.getCurDaYun(req.getSex(), age);
        //流年
        LiuNian curLiuYear = curDaYun.getCurLiuNian(curYear);
        String ganZhi = curLiuYear.getGanZhi();
        paiPanZhuInfo.setGan(ganZhi.charAt(0) + "");
        paiPanZhuInfo.setZhi(ganZhi.charAt(1) + "");

        return paiPanZhuInfo;
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
        //月柱-日柱空亡
        monthZhu.setKongWang(eightChar.monthKongWang());
        return monthZhu;
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
        yearZhu.setKongWang(eightChar.yearKongWang());
        //五合
        return yearZhu;
    }

    private static PersonDetailVO getPersonDetailVO(BaZiPaiPanReq req, Lunar lunar) {
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

    public Lunar getLunar(BaZiPaiPanReq req) {
        if (req.getTimeType() == 1) {
            return new Lunar(req.getYear(), req.getMonth(), req.getDay());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, req.getYear());
        calendar.set(Calendar.MONTH, req.getMonth());
        calendar.set(Calendar.DATE, req.getDay());
        return Lunar.fromDate(calendar.getTime());
    }

    public static void main(String[] args) {

        BaZiPaiPanReq req = new BaZiPaiPanReq();
        req.setYear(1993);
        req.setMonth(9);
        req.setDay(24);
        req.setHour(10);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, req.getYear());
        calendar.set(Calendar.MONTH, req.getMonth());
        calendar.set(Calendar.DATE, req.getDay());

        int age = Calendar.get


        Lunar lunar = Lunar.fromDate(calendar.getTime());

        String yearGan = lunar.getYearGan();
        System.out.println("日干:" + yearGan);
        String yearZhi = lunar.getYearZhi();
        System.out.println("地支:" + yearZhi);
        String monthGan = lunar.getMonthZhi();
        System.out.println("月干:" + monthGan);
        String lunarYearGanByLiChun = lunar.getYearGanByLiChun();
        System.out.println("流运年干:" + lunarYearGanByLiChun);
        String lunarYearZhiByLiChun = lunar.getYearZhiByLiChun();
        System.out.println("流运地支:" + lunarYearZhiByLiChun);
        String monthXun = lunar.getMonthXun();
        System.out.println("月运：" + monthXun);

        //年柱
        EightChar eightChar = lunar.getEightChar();
        PaiPanZhuInfo yearZhu = new PaiPanZhuInfo();
        yearZhu.setName("年柱");
        yearZhu.setShiShen(eightChar.getYearShiShenGan());
        yearZhu.setGan(eightChar.getYearGan());
        yearZhu.setZhi(eightChar.getYearZhi());
        yearZhu.setCangGan(eightChar.getYearHideGan());
        yearZhu.setCangGanShishen(eightChar.getShiShenByGan(eightChar.getDayGan(), eightChar.getYearHideGan()));
        yearZhu.setShiErGongWei(eightChar.getShiErGongWei(eightChar.getDayGan(), eightChar.getYearZhi()));
        yearZhu.setNaYin(eightChar.getYearNaYin());
        System.out.println(yearZhu);

        String yearXunKong = eightChar.getYearXunKong();
        System.out.println(eightChar.getMonthXunKong());
        System.out.println(yearXunKong);
        String preGanByOffset = LunarUtil.getPreGanByOffset(-3, "午");
        System.out.println(preGanByOffset);

        Yun yun = eightChar.getCurDaYun(1, );

        DaYun[] daYun = yun.getDaYun();
        System.out.println(daYun[0].getEndAge());
        System.out.println(daYun[0].getEndYear());
        System.out.println(daYun[1].getStartYear());
        System.out.println(daYun[1].getEndYear());
        System.out.println(daYun[2].getStartYear());
        System.out.println(daYun[2].getEndYear());
        System.out.println(daYun[10].getStartYear());
        System.out.println(daYun[10].getEndYear());
        //


    }


}
