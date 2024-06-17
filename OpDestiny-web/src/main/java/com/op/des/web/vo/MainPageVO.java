package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPageVO implements Serializable {
    private JiShenVO jiShen;
    private YiVO yi;
    private HuangLiVO huangLiVO;
    private JiVO jiVO;
    private XiongShaVO xiongShaVO;
    private BaiJiVO baiJiVO;
    private TodaySituationVO todaySituationVO;
    private JieQIVO jieQIVO;
}
