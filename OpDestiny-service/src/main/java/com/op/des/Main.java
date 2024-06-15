package com.op.des;

import com.nlf.calendar.EightChar;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.eightchar.DaYun;
import com.nlf.calendar.eightchar.XiaoYun;
import com.nlf.calendar.eightchar.Yun;
import com.nlf.calendar.util.LunarUtil;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1987);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DATE, 24);


        Lunar lunar = Lunar.fromDate(calendar.getTime());
        EightChar eightChar = lunar.getEightChar();
        System.out.println(eightChar);
        String dayDiShi = eightChar.getDayDiShi();
        System.out.println("八字：" + dayDiShi);
        System.out.println("十神【年】：" + eightChar.getYearShiShenGan());
        System.out.println("干【年】：" + eightChar.getYearGan());
        System.out.println("支【年】：" + eightChar.getYearZhi());
        System.out.println("藏干支【年】：" + eightChar.getYearHideGan());
        System.out.println("藏十神【年】：" + eightChar.getYearShiShenZhi());
        System.out.println("藏十神【年】：" + eightChar.getYearShiShenZhi());
        System.out.println("十二宫位【年】：" + eightChar.getYearDiShi());
        System.out.println("纳音五行【年】：" + eightChar.getYearNaYin());
//        System.out.println("神煞【年】：" + eightChar.getshen());
        System.out.println("旬空【年】：" + eightChar.getMonthXunKong());

        Yun yun = eightChar.getYun(1);
        DaYun[] daYun = yun.getDaYun();
        XiaoYun[] xiaoYun = daYun[0].getXiaoYun();
        String xun = xiaoYun[0].getXun();
        System.out.println(xun);

    }
}
