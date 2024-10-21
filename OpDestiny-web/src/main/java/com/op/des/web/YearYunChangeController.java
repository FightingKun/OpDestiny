package com.op.des.web;

import com.google.common.collect.Lists;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.param.YearYunLiuChangeReq;
import com.op.des.web.vo.YearYunLiuChangeVO;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 年运流变
 */
@RestController
@RequestMapping("/op/des/year/yun")
public class YearYunChangeController {

    @ResponseBody
    @RequestMapping("/main")
    public YearYunLiuChangeVO yearYunLiuChangeMain(YearYunLiuChangeReq req) {
        //未登陆，则给假数据
        YearYunLiuChangeVO yearYunLiuChangeVO = new YearYunLiuChangeVO();
        if (StringUtils.isEmpty(req.getToken())) {
            List<YearYunLiuChangeVO.PaiPanBaseInfo> zhus = new ArrayList<>();
            //年柱
            YearYunLiuChangeVO.PaiPanBaseInfo yearZhu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            yearZhu.setName("年柱");
            yearZhu.setShiShen("劫财");
            yearZhu.setGan("丁");
            yearZhu.setZhi("卯");
            yearZhu.setCangGan(Lists.newArrayList("乙"));
            yearZhu.setCangGanShishen(Lists.newArrayList("印"));
            yearZhu.setShiErGongWei("沐浴");
            zhus.add(yearZhu);
            //月柱
            YearYunLiuChangeVO.PaiPanBaseInfo monthZhu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            monthZhu.setName("月柱");
            monthZhu.setShiShen("伤官");
            monthZhu.setGan("己");
            monthZhu.setZhi("酉");
            monthZhu.setCangGan(Lists.newArrayList("辛"));
            monthZhu.setCangGanShishen(Lists.newArrayList("财"));
            monthZhu.setShiErGongWei("死");
            zhus.add(monthZhu);
            //日柱
            YearYunLiuChangeVO.PaiPanBaseInfo dayZhu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            dayZhu.setName("日柱");
            dayZhu.setShiShen("日");
            dayZhu.setGan("丙");
            dayZhu.setZhi("子");
            dayZhu.setCangGan(Lists.newArrayList("癸"));
            dayZhu.setCangGanShishen(Lists.newArrayList("官"));
            dayZhu.setShiErGongWei("胎");
            zhus.add(dayZhu);

            //时柱
            YearYunLiuChangeVO.PaiPanBaseInfo timeZhu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            timeZhu.setName("时柱");
            timeZhu.setShiShen("正官");
            timeZhu.setGan("癸");
            timeZhu.setZhi("巳");
            timeZhu.setCangGan(Lists.newArrayList("丙庚戊"));
            timeZhu.setCangGanShishen(Lists.newArrayList("比财食"));
            timeZhu.setShiErGongWei("临官");
            zhus.add(timeZhu);
            //大运
            YearYunLiuChangeVO.PaiPanBaseInfo daYunZhu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            daYunZhu.setName("大运");
            daYunZhu.setShiShen("正印");
            daYunZhu.setGan("乙");
            daYunZhu.setZhi("巳");
            daYunZhu.setCangGan(Lists.newArrayList("丙庚戊"));
            daYunZhu.setCangGanShishen(Lists.newArrayList("比财食"));
            daYunZhu.setShiErGongWei("临官");
            zhus.add(daYunZhu);

            //流年
            YearYunLiuChangeVO.PaiPanBaseInfo yearLiu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            yearLiu.setName("流年");
            yearLiu.setShiShen("偏印");
            yearLiu.setGan("甲");
            daYunZhu.setZhi("辰");
            yearLiu.setCangGan(Lists.newArrayList("戊己癸"));
            yearLiu.setCangGanShishen(Lists.newArrayList("食印官"));
            yearLiu.setShiErGongWei("冠带");
            zhus.add(yearLiu);
            //流月
            YearYunLiuChangeVO.PaiPanBaseInfo monthLiu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            monthLiu.setName("流月");
            monthLiu.setShiShen("比肩");
            monthLiu.setGan("丙");
            monthLiu.setZhi("寅");
            monthLiu.setCangGan(Lists.newArrayList("甲丙戊"));
            monthLiu.setCangGanShishen(Lists.newArrayList("枭比食"));
            monthLiu.setShiErGongWei("长生");
            zhus.add(monthLiu);
            //流日
            YearYunLiuChangeVO.PaiPanBaseInfo dayLiu = new YearYunLiuChangeVO.PaiPanBaseInfo();
            dayLiu.setName("流日");
            dayLiu.setShiShen("食神");
            dayLiu.setGan("戊");
            dayLiu.setZhi("戌");
            dayLiu.setCangGan(Lists.newArrayList("戊辛丁"));
            dayLiu.setCangGanShishen(Lists.newArrayList("食财劫"));
            dayLiu.setShiErGongWei("墓");
            zhus.add(dayLiu);
            yearYunLiuChangeVO.setPaiPanBaseInfos(zhus);
            yearYunLiuChangeVO.setUserName("你好，用户名");
            Lunar lunar = Lunar.fromDate(new Date());
            String ganZhi = lunar.getYearGan() + lunar.getYearZhi() + "年"
                    + lunar.getMonthGan() + lunar.getMonthZhi() + "月"
                    + lunar.getDayGan() + lunar.getDayZhi() + "日";
            yearYunLiuChangeVO.setTodayGanZhi("今日干支：" + ganZhi);

            String solarDay = lunar.getSolar().toYmd();
            yearYunLiuChangeVO.setSolarDay("公历：" + solarDay);
            String lunarDay = lunar.getYearGan() + lunar.getYearZhi() + "年"
                    + lunar.getMonthInChinese()+"月" + lunar.getDayInChinese();
            yearYunLiuChangeVO.setLunarDay(lunarDay);


        }

        return yearYunLiuChangeVO;

    }


}
