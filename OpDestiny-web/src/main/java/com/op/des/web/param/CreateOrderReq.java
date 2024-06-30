package com.op.des.web.param;

import lombok.Data;

@Data
public class CreateOrderReq {
    private String token;
    private Long amount;
}
