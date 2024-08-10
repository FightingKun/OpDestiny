package com.op.des.web.domain;

import com.op.des.web.lunar.Lunar;
import com.op.des.web.vo.BaZiPaiPanPageVO;
import com.op.des.web.vo.PaiPanInfoVO;
import com.op.des.web.vo.PaiPanZhuInfo;

import java.util.List;
import java.util.Objects;

public class Picture {

    public static String zhengBaGe(Lunar lunar, BaZiPaiPanPageVO baZiPaiPanPageVO) {
        PaiPanInfoVO paiPanInfoVO = baZiPaiPanPageVO.getPaiPanInfoVO();
        PaiPanZhuInfo monthZhu = paiPanInfoVO.getMonthZhu();
        PaiPanZhuInfo dayZhu = paiPanInfoVO.getDayZhu();
        String shiShen = dayZhu.getShiShen();
        List<String> cangGanShishen = monthZhu.getCangGanShishen();
        for (int i = 0; i < cangGanShishen.size(); i++) {
            if (Objects.equals(cangGanShishen.get(i), shiShen)) {
                return shiShen + "格";
            }

        }

        return null;
    }
}
