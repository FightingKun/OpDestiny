package com.op.des.web.param;

import com.op.des.web.vo.userinfo.UserVO;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class UserLoginParam {
    private String phone;
    private String token;
    private String smsCode;
    private String type;

    String regex = "^1[3-9]\\d{9}$";

    public UserVO checkPhone() {
        UserVO userVO = new UserVO();
        userVO.setStatus(100);
        if (StringUtils.isEmpty(phone)) {
            userVO.setMessage("您的手机号为空");
            return userVO;
        }
        if (!validatePhoneNumber(phone)) {
            userVO.setMessage("您的手机号非法");
            return userVO;
        }
        return null;
    }

    public UserVO checkSmsCode() {
        UserVO userVO = new UserVO();
        userVO.setStatus(100);
        if (StringUtils.isEmpty(smsCode)) {
            userVO.setMessage("短信验证码为空");
            return userVO;
        }
        return null;
    }

    private boolean validatePhoneNumber(String phoneNum) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }
}
