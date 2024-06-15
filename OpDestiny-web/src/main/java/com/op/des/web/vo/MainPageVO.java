package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPageVO {
    private String pageTitle;
    private String fourPillarsDestiny;
    private String lunar;
    private String annualMoveChanges;
    private String studyNumerology;

}
