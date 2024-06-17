package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaZiPaiPanPageVO implements Serializable {
    /**
     * 命主基础信息
     */
    PersonDetailVO personDetailVO;
    /**
     * 排盘信息
     */
    PaiPanInfoVO paiPanInfoVO;

    /**
     * 运详细信息
     */
    YunInfoVO yunInfoVO;
}
