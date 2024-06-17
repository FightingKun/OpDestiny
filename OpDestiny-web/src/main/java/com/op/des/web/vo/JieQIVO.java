package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 节气
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JieQIVO implements Serializable {
    private String titleName;
    private List<String> contents;
}
