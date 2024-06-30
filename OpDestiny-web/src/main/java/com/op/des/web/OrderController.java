package com.op.des.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.op.des.web.param.CreateOrderReq;
import com.op.des.web.param.PayNotifyReq;
import com.op.des.web.service.wxpay.WxPayService;
import com.op.des.web.vo.order.OrderInfoVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("op/des/order")
public class OrderController {

    @Value("${private.key}")
    private String privateKey;

    @Autowired
    private WxPayService wxPayService;

    @RequestMapping("/create")
    public OrderInfoVO createOrder(CreateOrderReq req) {
        if (StringUtils.isEmpty(req.getToken())) {
            return new OrderInfoVO();
        }
        DecodedJWT decoded;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(privateKey)).build();
            //验证Token(验证失败，会引发异常)
            decoded = jwtVerifier.verify(req.getToken());
        } catch (Exception e) {
            return new OrderInfoVO();
        }
        Map<String, Claim> claims = decoded.getClaims();
        if (claims == null) {
            return new OrderInfoVO();
        }
        //用户id
        Long userid = claims.get("userid").asLong();
        //用户名
        String username = claims.get("username").asString();
        //手机号
        String phone = claims.get("phone").asString();
        return wxPayService.createOrder(req, userid, username, phone);
    }

    @RequestMapping("/notify")
    public String notifyPayRes(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) {
        return wxPayService.wxpayNotify(jsonObject, request, response);
    }
}
