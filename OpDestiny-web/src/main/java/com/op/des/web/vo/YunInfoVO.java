package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunInfoVO implements Serializable {
    private List<DaYunItem> daYuns;
    private List<YearLiuItem> yearLius;
    private List<String> monthLius;
    private List<String> dayLius;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearLiuItem {
        private String shiShen;
        private String ganZhi;
        private Integer year;
        private Integer age;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaYunItem {
        private String name;
        private String shiShen;
        private String ganZhi;
        private String shiErGongWei;
        private int yunEndTime;
        private int age;
    }
}
