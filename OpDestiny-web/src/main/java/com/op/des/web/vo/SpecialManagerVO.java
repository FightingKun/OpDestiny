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
public class SpecialManagerVO implements Serializable {

    Integer status;
    String message;

    public static SpecialManagerVO success() {
        SpecialManagerVO specialManagerVO = new SpecialManagerVO();
        specialManagerVO.setStatus(200);
        specialManagerVO.setMessage("操作成功");
        return specialManagerVO;
    }

    public static SpecialManagerVO fail() {
        SpecialManagerVO specialManagerVO = new SpecialManagerVO();
        specialManagerVO.setStatus(100);
        specialManagerVO.setMessage("操作失败");
        return specialManagerVO;
    }
}
