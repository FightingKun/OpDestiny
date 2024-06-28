package com.op.des.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfo {
    private Long loginTime;
    private Long outTime;
    private Long userId;
    private String userName;
    private String phone;
}
