package com.op.des.web.param;

import lombok.Data;

@Data
public class UserRegistParam {
    private String phone;
    private String name;
    private String pwd;
    private String sex;
}
