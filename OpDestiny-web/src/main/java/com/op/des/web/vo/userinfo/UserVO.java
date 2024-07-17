package com.op.des.web.vo.userinfo;

import com.op.des.web.vo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends BaseVO {
    private Long id;
    private String name;
    private String phone;
    private String token;
}
