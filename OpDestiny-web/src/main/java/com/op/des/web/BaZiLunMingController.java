package com.op.des.web;

import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.JieQi;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.eightchar.DaYun;
import com.op.des.web.lunar.eightchar.LiuNian;
import com.op.des.web.lunar.eightchar.LiuYue;
import com.op.des.web.param.BaZiPaiPanReq;
import com.op.des.web.utils.ChineseCalendarUtils;
import com.op.des.web.vo.BaZiPaiPanPageVO;
import com.op.des.web.vo.PaiPanInfoVO;
import com.op.des.web.vo.PaiPanZhuInfo;
import com.op.des.web.vo.PersonDetailVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 八字论命
 */
@RestController("op/des/bazilunming")
public class BaZiLunMingController {

    @RequestMapping("/paipan")
    @ResponseBody
    public BaZiPaiPanPageVO paiPan(BaZiPaiPanReq req) {

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
        //日柱
        paiPanInfoVO.setDayZhu(dayZhu(eightChar));
        //时柱
        paiPanInfoVO.setDayZhu(timeZhu(eightChar));

        //获取大运
        DaYun curDaYun = eightChar.getCurDaYun(req.getSex(), age);
        paiPanInfoVO.setDaYun();
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
}
