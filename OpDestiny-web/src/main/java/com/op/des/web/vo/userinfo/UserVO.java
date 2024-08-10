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

    public static UserVO createSendSms(String phone) {
        UserVO userVO = new UserVO();
        userVO.setStatus(200);
        userVO.setPhone(phone);
        userVO.setMessage("请输入短信验证码");
        return userVO;
    }

    public static UserVO createLogin(String token) {
        UserVO userVO = new UserVO();
        userVO.setStatus(200);
        userVO.setToken(token);
        userVO.setMessage("已登录");
        return userVO;
    }

    public static UserVO verifyError() {
        UserVO userVO = new UserVO();
        userVO.setStatus(100);
        userVO.setMessage("短信验证失败，请稍后重试");
        return userVO;
    }

    public static UserVO loginError() {
        UserVO userVO = new UserVO();
        userVO.setStatus(100);
        userVO.setMessage("出现异常请稍后重试");
        return userVO;
    }

    public static UserVO verifySuccess(String token) {
        UserVO userVO = new UserVO();
        userVO.setStatus(200);
        userVO.setToken(token);
        userVO.setMessage("登录成功");
        return userVO;
    }
}
