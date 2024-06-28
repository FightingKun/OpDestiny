package com.op.des.web;


import com.op.des.web.dao.mapper.UserInfoPOMapper;
import com.op.des.web.dao.po.UserInfoPO;
import com.op.des.web.dao.po.UserInfoPOCriteria;
import com.op.des.web.domain.UserLoginInfo;
import com.op.des.web.utils.UserLoginInfoCacheUtil;
import com.op.des.web.vo.userinfo.UserVO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("op/des/user")
public class UserInfoController {

    private static Long _30_MIN = 30 * 60 * 1000L;

    @Autowired
    private UserInfoPOMapper userInfoPOMapper;

    @RequestMapping("/login")
    public UserVO login(UserInfoPO userInfo) {

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(userLoginInfo.getUserId());
        //缓存中是否存在
        Pair<Boolean, UserLoginInfo> login = UserLoginInfoCacheUtil.isLogin(userLoginInfo);
        if (login.getKey()) {
            UserLoginInfo loginInfo = login.getValue();
            return new UserVO(loginInfo.getUserId(), loginInfo.getUserName(), loginInfo.getPhone());
        }

        UserInfoPOCriteria example = new UserInfoPOCriteria();
        UserInfoPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(userInfo.getPhone());
        criteria.andPaswEqualTo(userInfo.getPasw());
        List<UserInfoPO> userInfos = userInfoPOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userInfos)) {
            return new UserVO();
        }
        UserInfoPO loginInfo = userInfos.get(0);
        return new UserVO(loginInfo.getId(), loginInfo.getUsername(), loginInfo.getPhone());
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
            return new UserVO(userInfoPO.getId(), userInfoPO.getUsername(), userInfoPO.getPhone());
        }
        return new UserVO();
    }
}
