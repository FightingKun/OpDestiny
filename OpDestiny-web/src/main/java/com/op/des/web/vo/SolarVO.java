package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 节气
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolarVO {
    private String titleName;
    private List<String> contents;
}
