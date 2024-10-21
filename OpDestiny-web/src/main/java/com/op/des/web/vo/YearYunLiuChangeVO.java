package com.op.des.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class YearYunLiuChangeVO {
    List<PaiPanBaseInfo> paiPanBaseInfos;
    String userName;
    String todayGanZhi;
    String solarDay;
    String lunarDay;
    String jieQi;
    List<String> liuTips;
    List<String> dayZhuTips;


    @Data
    public static class PaiPanBaseInfo {
        String name;
        String shiShen;
        String gan;
        String zhi;
        List<String> cangGan;
        List<String> cangGanShishen;
        String shiErGongWei;
    }

}
