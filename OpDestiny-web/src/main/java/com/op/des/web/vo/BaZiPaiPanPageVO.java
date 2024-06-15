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
    PersonDetailVO personDetailVO;
    PaiPanInfoVO paiPanInfoVO;
    PaiPanInfoDetailVO paiPanInfoDetailVO;
}
