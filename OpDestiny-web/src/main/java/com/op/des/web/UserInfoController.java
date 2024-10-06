package com.op.des.web;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.op.des.web.dao.mapper.UserInfoPOMapper;
import com.op.des.web.dao.po.UserInfoPO;
import com.op.des.web.dao.po.UserInfoPOCriteria;
import com.op.des.web.domain.UserLoginInfo;
import com.op.des.web.param.UserLoginParam;
import com.op.des.web.param.UserRegistParam;
import com.op.des.web.utils.HttpUtils;
import com.op.des.web.vo.userinfo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/op/des/user")
@Slf4j
public class UserInfoController {

    private static Long _60_MIN = 60 * 60 * 1000L;

    private static String LOGIN = "LOGIN";
    private static String REGIST = "REGIST";

    @Autowired
    private UserInfoPOMapper userInfoPOMapper;
    @Value("${private.key}")
    private String privateKey;
    String regex = "^1[3-9]\\d{9}$";
    // 创建缓存
    static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(300, TimeUnit.SECONDS) // 缓存写入后5分钟过期
            .build(new CacheLoader<String, String>() {
                // 缓存加载函数
                @Override
                public String load(String key) throws Exception {
                    return "phone_" + key;
                }
            });

    @RequestMapping("/login")
    public UserVO login(UserLoginParam userLoginParam) {
        String phone = userLoginParam.getPhone();
        if (StringUtils.isEmpty(userLoginParam.getToken())) {
            UserVO userVO = userLoginParam.checkPhone();
            if (userVO != null) {
                return userVO;
            }

            UserInfoPOCriteria example = new UserInfoPOCriteria();
            UserInfoPOCriteria.Criteria criteria = example.createCriteria();
            criteria.andPhoneEqualTo(phone);
            List<UserInfoPO> userInfos = userInfoPOMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(userInfos)) {
                userVO = new UserVO();
                userVO.setStatus(100);
                userVO.setMessage("用户未注册，请先注册！");
            }
            //发送短信
            UserVO loginError = sendSms(LOGIN, phone);
            if (loginError != null) return loginError;
            return UserVO.createSendSms(phone);
        }

        String token = userLoginParam.getToken();
        DecodedJWT decoded = null;
        try {
            JWTVerifier require = JWT.require(Algorithm.HMAC256(privateKey)).build();
            decoded = require.verify(token);
        } catch (Exception e) {
            //发送短信
            //生成短信验证码
            UserVO loginError = sendSms(LOGIN, phone);
            if (loginError != null) return loginError;
            return UserVO.createSendSms(phone);
        }
        if (decoded == null) {
            //发送短信
            //生成短信验证码
            UserVO loginError = sendSms(LOGIN, phone);
            if (loginError != null) return loginError;
            return UserVO.createSendSms(phone);
        }
        Map<String, Claim> claims = decoded.getClaims();
        if (claims == null) {
            //发送短信
            //生成短信验证码
            UserVO loginError = sendSms(LOGIN, phone);
            if (loginError != null) return loginError;
        }

        //token登陆
        Long userid = claims.get("userid").asLong();
        String username = claims.get("username").asString();
        phone = claims.get("phone").asString();
        return UserVO.createLogin(generateToken(userid, username, phone));
    }

    private UserVO sendSms(String type, String phone) {
        String smsCode = generateSmsCode();
        try {
            if (!HttpUtils.doGet(phone, smsCode)) return UserVO.loginError();
        } catch (Exception exception) {
            return UserVO.loginError();
        }
        //保存验证码
        cache.put(phone + "_" + type, smsCode);
        return null;
    }

    @RequestMapping("/verify")
    public UserVO verify(UserLoginParam userLoginParam) {
        UserVO userVO = userLoginParam.checkPhone();
        if (userVO != null) {
            return userVO;
        }
        userVO = userLoginParam.checkSmsCode();
        if (userVO != null) {
            return userVO;
        }
        String type = LOGIN;
        if (!StringUtils.isEmpty(userLoginParam.getType())) {
            type = userLoginParam.getType();
        }
        try {
            String s = cache.get(userLoginParam.getPhone() + "_" + type);
            if (Objects.equals(s, userLoginParam.getSmsCode())) {
                UserInfoPOCriteria example = new UserInfoPOCriteria();
                UserInfoPOCriteria.Criteria criteria = example.createCriteria();
                criteria.andPhoneEqualTo(userLoginParam.getPhone());
                List<UserInfoPO> userInfos = userInfoPOMapper.selectByExample(example);
                UserInfoPO userInfoPO = userInfos.get(0);
                return UserVO.verifySuccess(generateToken(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone()));
            }
            return UserVO.verifyError();
        } catch (ExecutionException e) {
            log.info("验证失败");
            return UserVO.verifyError();
        }
    }

    public boolean validatePhoneNumber(String phoneNum) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    private String generateSmsCode() {
        return generateVerificationCode();
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10); // 生成0-9的随机数
            sb.append(randomDigit);
        }
        return sb.toString();
    }

    @RequestMapping("/regist")
    public UserVO regist(UserRegistParam registParam) {
        String phone = registParam.getPhone();
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

        UserInfoPOCriteria example = new UserInfoPOCriteria();
        UserInfoPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(registParam.getPhone());
        List<UserInfoPO> userInfoPOS = userInfoPOMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(userInfoPOS)) {
            userVO = new UserVO();
            userVO.setStatus(100);
            userVO.setMessage("手机号已经注册，请登录");
            return userVO;
        }

        //发送短信
        sendSms(REGIST, registParam.getPhone());

        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setPhone(registParam.getPhone());
        userInfoPO.setSex(registParam.getSex());
        userInfoPO.setUsername(registParam.getName());
        userInfoPO.setPasw(registParam.getPwd());
        userInfoPO.setAge(registParam.getAge());
        userInfoPO.setBirthaddress(registParam.getBirthAddress());
        userInfoPO.setCurrentaddress(registParam.getCurrentAddress());
        userInfoPO.setBirthday(registParam.getShengChen());
        int i = userInfoPOMapper.insertSelective(userInfoPO);
        if (i != 0) {
            return UserVO.createSendSms(userInfoPO.getPhone());
        }
        return UserVO.registError();
    }

    private String generateToken(Long userId, String userName, String phone) {
        JWTCreator.Builder jwtBuilder = JWT.create();
        return jwtBuilder.withClaim("userid", userId).withClaim("username", userName).withClaim("phone", phone).withExpiresAt(new Date(System.currentTimeMillis() + _60_MIN)).sign(Algorithm.HMAC256(privateKey));
    }
}
