package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiPanInfoDetailVO {
    private List<daYunItem> daYuns;
    private List<yearLiuItem> yearLius;
    private List<String> monthLius;
    private List<String> dayLius;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class yearLiuItem{
        private int order;
        private String ganYear;
        private String time;
        private int age;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class daYunItem{
        private int order;
        private String shen;
        private String ganYear;
        private String yun;

        private String time;
        private int age;
    }
}
