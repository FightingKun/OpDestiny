package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 凶煞
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FierceVO {
    private String titleName;
    private List<String> contents;
}
