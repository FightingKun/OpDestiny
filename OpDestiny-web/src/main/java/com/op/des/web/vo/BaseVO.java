package com.op.des.web.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseVO implements Serializable {
    private Integer status;
    private String message;
}
