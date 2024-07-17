package com.op.des.web;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.op.des.web.dao.mapper.UserInfoPOMapper;
import com.op.des.web.dao.po.UserInfoPO;
import com.op.des.web.dao.po.UserInfoPOCriteria;
import com.op.des.web.domain.UserLoginInfo;
import com.op.des.web.param.UserLoginParam;
import com.op.des.web.param.UserRegistParam;
import com.op.des.web.vo.userinfo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/op/des/user")
public class UserInfoController {

    private static Long _60_MIN = 60 * 60 * 1000L;

    @Autowired
    private UserInfoPOMapper userInfoPOMapper;
    @Value("${private.key}")
    private String privateKey;

    @RequestMapping("/login")
    public UserVO login(UserLoginParam userLoginParam) {
        String phone = null;
        String pwd = null;
        if (StringUtils.isEmpty(userLoginParam.getToken())) {
            phone = userLoginParam.getPhone();
            pwd = userLoginParam.getPwd();
            UserInfoPOCriteria example = new UserInfoPOCriteria();
            UserInfoPOCriteria.Criteria criteria = example.createCriteria();
            criteria.andPhoneEqualTo(phone);
            criteria.andPaswEqualTo(pwd);
            List<UserInfoPO> userInfos = userInfoPOMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(userInfos)) {
                return new UserVO();
            }
            UserInfoPO userInfoPO = userInfos.get(0);
            return new UserVO(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone(), generateToken(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone()));
        }
        String token = userLoginParam.getToken();
        DecodedJWT decoded;
        try {
            JWTVerifier require = JWT.require(Algorithm.HMAC256(privateKey)).build();
            decoded = require.verify(token);
        } catch (Exception e) {
            return new UserVO();
        }
        Map<String, Claim> claims = decoded.getClaims();
        if (claims == null) {
            return new UserVO();
        }

        //token登陆
        Long userid = claims.get("userid").asLong();
        String username = claims.get("username").asString();
        phone = claims.get("phone").asString();
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(userid);
        return new UserVO(userid, username, phone, generateToken(userid, username, phone));
    }

    @RequestMapping("/regist")
    public UserVO regist(UserRegistParam registParam) {
        UserInfoPOCriteria example = new UserInfoPOCriteria();
        UserInfoPOCriteria.Criteria criteria = example.createCriteria();
        if (registParam.getPhone() != null) {
            criteria.andPhoneEqualTo(registParam.getPhone());
        }
        List<UserInfoPO> userInfoPOS = userInfoPOMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(userInfoPOS)) {
            UserVO userVO = new UserVO();
            userVO.setStatus(100);
            userVO.setMessage("手机号已经注册，请登录");
            return userVO;
        }

        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setPhone(registParam.getPhone());
        userInfoPO.setSex(registParam.getSex());
        userInfoPO.setUsername(registParam.getName());
        userInfoPO.setPasw(registParam.getPwd());
        int i = userInfoPOMapper.insertSelective(userInfoPO);
        if (i != 0) {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            //生成token
            userLoginInfo.setUserId(userInfoPO.getId());
            userLoginInfo.setPhone(userInfoPO.getPhone());
            userLoginInfo.setLoginTime(System.currentTimeMillis());
            userLoginInfo.setOutTime(System.currentTimeMillis() + _60_MIN);
            return new UserVO(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone(), generateToken(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone()));
        }
        return new UserVO();
    }

    private String generateToken(Long userId, String userName, String phone) {
        JWTCreator.Builder jwtBuilder = JWT.create();
        return jwtBuilder
                .withClaim("userid", userId)
                .withClaim("username", userName)
                .withClaim("phone", phone)
                .withExpiresAt(new Date(System.currentTimeMillis() + _60_MIN))
                .sign(Algorithm.HMAC256(privateKey));
    }
}
