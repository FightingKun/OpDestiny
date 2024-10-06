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
public class SpecialLuVO implements Serializable {

    private WorkVO workVO;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class WorkVO{
        private String name;
        private String content;
    }
}
