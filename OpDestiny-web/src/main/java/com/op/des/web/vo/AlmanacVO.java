package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 黄历
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlmanacVO {
    /**
     * 宜
     */
    private SuitableVO suitable;
    /**
     * 忌
     */
    private TaboosVO taboos;

    /**
     * 日历
     */
    private AlmanacVO almanac;
    /**
     * 吉神
     */
    private DeityVO deity;
    /**
     * 百忌
     */
    private BaijiVO baiJi;
    /**
     * 节气
     */
    private SolarVO solar;
    /**
     * 凶煞
     */
    private FierceVO fierce;

    /**
     * 今日状况
     */
    private TodaySituationVO todaySituation;

    /**
     * 二维码
     */
    private String qRCodeUrl;
}
