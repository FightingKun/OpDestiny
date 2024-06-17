package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 忌
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YiVO implements Serializable {
    private String  titleName;
    private String subTitle;
    private List<String> contents;
    private String questionName;
    private String questionUrl;
}
