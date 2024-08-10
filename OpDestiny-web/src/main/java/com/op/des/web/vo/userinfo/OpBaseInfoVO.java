package com.op.des.web.vo.userinfo;

import lombok.Data;

import java.util.List;

@Data
public class OpBaseInfoVO {

    /**
     * 五行
     */
    List<String> wuXing;
    /**
     * 调侯
     */
    List<String> tiaoHou;
    /**
     * 格局
     */
    List<String> geJu;
    /**
     * 提要
     */
    List<String> tiYao;
    /**
     * 称骨
     */
    List<String> chenGu;
    /**
     * 喜忌
     */
    List<String> xiJi;
    /**
     * 吉运
     */
    List<String> jiYun;
}
