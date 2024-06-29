package com.op.des.web.vo.userinfo;

import jdk.nashorn.internal.parser.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String token;
}
