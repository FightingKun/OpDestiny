package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 忌讳
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaboosVO {
    private String  titleName;
    private String subTitle;
    private List<String> contents;
    private String questionName;
    private String questionUrl;
}
