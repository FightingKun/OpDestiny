/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.op.des.web;

import com.google.common.collect.Lists;
import com.op.des.web.lunar.Lunar;
import com.op.des.web.lunar.Solar;
import com.op.des.web.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
public class MainPageController {
    @RequestMapping("/op/des/main")
    @ResponseBody
    public MainPageVO mainPage(@RequestParam("date") String dateStr) {
        MainPageVO mainPageVO = new MainPageVO();
        Date date;
        LocalDate localDate;
        if (StringUtils.isEmpty(dateStr)) {
            localDate = LocalDate.now();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(dateStr, dateTimeFormatter);
        }
        // 转换为ZonedDateTime
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        // 转换为Instant
        java.time.Instant instant = zonedDateTime.toInstant();
        // 转换为Date
        date = Date.from(instant);

        Solar solar = Solar.fromDate(date);
        Lunar lunar = solar.getLunar();
        //吉神
        List<String> dayJiShen = lunar.getDayJiShen();
        JiShenVO jiShenVO = new JiShenVO();
        jiShenVO.setTitleName("吉神宜趋");
        jiShenVO.setContents(dayJiShen);
        mainPageVO.setJiShen(jiShenVO);

        //宜
        YiVO yiVO = new YiVO();
        List<String> dayYi = lunar.getDayYi();
        yiVO.setTitleName("宜");
        yiVO.setContents(dayYi);
        mainPageVO.setYi(yiVO);
        //黄历
        HuangLiVO huangLiVO = new HuangLiVO();
        huangLiVO.setDay(solar.getDay());
        huangLiVO.setTitleDate(solar.getYear() + "年" + solar.getMonth() + "月" + solar.getDay() + "日");
        huangLiVO.setWeekDay(lunar.getYearInChinese() + "年" + lunar.getMonthInChinese() + "月" + lunar.getDayInChinese() + " " + "第" + (lunar.getWeek() + 1) + "周 " + "周" + lunar.getWeekInChinese());
        huangLiVO.setGanzhi(Lists.newArrayList(lunar.getYearInGanZhi() + "年", lunar.getMonthInGanZhi() + "月", lunar.getDayInGanZhi() + "日"));
        mainPageVO.setHuangLiVO(huangLiVO);
        //忌
        JiVO jiVO = new JiVO();
        jiVO.setTitleName("忌");
        jiVO.setContents(lunar.getDayJi());
        mainPageVO.setJiVO(jiVO);
        //凶煞
        XiongShaVO xiongShaVO = new XiongShaVO();
        xiongShaVO.setTitleName("凶煞宜忌");
        xiongShaVO.setContents(lunar.getDayXiongSha());
        mainPageVO.setXiongShaVO(xiongShaVO);
        //百忌
        BaiJiVO baiJiVO = new BaiJiVO();
        baiJiVO.setTitleName("彭祖百忌");
        baiJiVO.setContent(lunar.getPengZuGan() + "," + lunar.getPengZuZhi());
        mainPageVO.setBaiJiVO(baiJiVO);

        //今日神
        TodaySituationVO todaySituationVO = new TodaySituationVO();
        todaySituationVO.setCaiShen(new TodaySituationVO.Item("财神", lunar.getDayPositionCaiDesc()));
        todaySituationVO.setXiShen(new TodaySituationVO.Item("喜神", lunar.getDayPositionXiDesc()));
        todaySituationVO.setFuShen(new TodaySituationVO.Item("福神", lunar.getDayPositionFuDesc()));
        todaySituationVO.setShengXiao(new TodaySituationVO.Item("今日冲生肖", lunar.getDayChongShengXiao()));
        todaySituationVO.setNaYin(new TodaySituationVO.Item("今日纳音", lunar.getDayNaYin()));
        todaySituationVO.setXingZuo(new TodaySituationVO.Item("今日星座", solar.getXingZuo()));
        todaySituationVO.setShaFang(new TodaySituationVO.Item("煞方", lunar.getDaySha()));
        mainPageVO.setTodaySituationVO(todaySituationVO);

        //节气
        JieQIVO jieQIVO = new JieQIVO();
        jieQIVO.setTitleName("二十四节气");
        jieQIVO.setContents(Lists.newArrayList(lunar.getPrevJieQi().getName(), lunar.getNextJie().getName()));
        mainPageVO.setJieQIVO(jieQIVO);
        return mainPageVO;
    }

}
