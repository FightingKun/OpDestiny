package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialQiVO implements Serializable {

    private YinYuanVO riZhu;
    private YinYuanVO gongWei;
    private YinYuanVO xingShen;
    private YinYuanVO shenSha;
    private YinYuanVO quJi;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class YinYuanVO{
        private String name;
        private String content;
    }
}
