package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 百忌
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaijiVO {
    private String titleName;
    private List<String> contents;
}
