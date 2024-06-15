package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 今日状况
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodaySituationVO {
    private Item cai;
    private Item xi;
    private Item fu;
    private Item chongXiao;
    private Item naYin;
    private Item xingZuo;
    private Item shaFang;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Item{
        private String titleName;
        private String content;
    }
}
