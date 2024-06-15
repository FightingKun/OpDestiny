package com.op.des.web.vo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiPanZhuInfo {
    String name;
    String shiShen;
    String gan;
    String zhi;
    List<String> cangGan;
    List<String> cangGanShishen;
    String shiErGongWei;
    String naYin;
    List<String> shenSha;
    String kongWang;
    List<String> ganZhiRelations = Lists.newArrayList();
}
