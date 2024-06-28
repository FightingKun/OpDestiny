package com.op.des.web.domain;


import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long userId;
    private String phone;
    private String userName;
    private Integer shouldAmount;
    private String content;
    private Long createTime;
    private Long updateTime;
    private int status;//0 初始化，1失败 2 成功


    public void success() {
        if (status == 0) {
            status = 1;
        }
    }

    public boolean isSuccess() {
        return status == 2;
    }
}
