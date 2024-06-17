package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 今日状况
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodaySituationVO implements Serializable {
    private Item caiShen;
    private Item xiShen;
    private Item fuShen;
    private Item shengXiao;
    private Item naYin;
    private Item xingZuo;
    private Item shaFang;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String titleName;
        private String content;
    }
}
