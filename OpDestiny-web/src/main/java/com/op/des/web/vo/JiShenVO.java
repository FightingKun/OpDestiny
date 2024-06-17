package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 吉神
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiShenVO implements Serializable {
    private String titleName;
    private List<String> contents;
}
