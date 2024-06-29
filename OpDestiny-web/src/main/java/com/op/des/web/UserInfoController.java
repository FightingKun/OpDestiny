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
import com.op.des.web.utils.UserLoginInfoCacheUtil;
import com.op.des.web.vo.userinfo.UserVO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("op/des/user")
public class UserInfoController {

    private static Long _30_MIN = 30 * 60 * 1000L;

    @Autowired
    private UserInfoPOMapper userInfoPOMapper;
    @Value("{private.key}")
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
            UserInfoPO loginInfo = userInfos.get(0);
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(userLoginInfo.getUserId());
            //保存缓存
            UserLoginInfoCacheUtil.login(userLoginInfo);
            return new UserVO(loginInfo.getId(), loginInfo.getUsername(), loginInfo.getPhone(), generateToken(loginInfo.getId(), loginInfo.getUsername(), loginInfo.getPhone()));
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
        //用户id
        Long userid = claims.get("userid").asLong();
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(userid);
        //缓存中是否存在
        Pair<Boolean, UserLoginInfo> login = UserLoginInfoCacheUtil.isLogin(userLoginInfo);
        if (login.getKey()) {
            UserLoginInfo loginInfo = login.getValue();
            return new UserVO(loginInfo.getUserId(), loginInfo.getUserName(), loginInfo.getPhone(), token);
        }
        return new UserVO();
    }

    @RequestMapping("/regist")
    public UserVO regist(UserInfoPO userInfo) {

        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setPhone(userInfo.getPhone());
        userInfoPO.setSex(userInfo.getSex());
        userInfoPO.setUsername(userInfo.getUsername());
        userInfoPO.setPasw(userInfo.getPasw());


        UserInfoPOCriteria example = new UserInfoPOCriteria();
        UserInfoPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(userInfo.getPhone());
        criteria.andPaswEqualTo(userInfo.getPasw());
        int i = userInfoPOMapper.insertSelective(userInfoPO);
        if (i != 0) {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            //写缓存
            userLoginInfo.setUserId(userInfoPO.getId());
            userLoginInfo.setPhone(userInfoPO.getPhone());
            userLoginInfo.setLoginTime(System.currentTimeMillis());
            userLoginInfo.setOutTime(System.currentTimeMillis() + _30_MIN);
            UserLoginInfoCacheUtil.login(userLoginInfo);
            return new UserVO(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone(), generateToken(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone()));
        }
        return new UserVO();
    }

    private String generateToken(Long userId, String userName, String phone) {
        JWTCreator.Builder jwtBuilder = JWT.create();
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "jwt");
        header.put("alg", "hs256");
        return jwtBuilder.withHeader(header).withClaim("userid", userId).withClaim("username", userName).withClaim("phone", phone).sign(Algorithm.HMAC256(privateKey));
    }
}
