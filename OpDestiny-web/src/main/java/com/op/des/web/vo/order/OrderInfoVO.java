package com.op.des.web.vo.order;


import lombok.Data;

@Data
public class OrderInfoVO {

    private String codeUrl;
    private String orderCode;
    private String companyName;
    private String shouldAmount;
    private Long expireTime;
}
