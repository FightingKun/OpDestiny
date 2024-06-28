package com.op.des.web.service.wxpay;

import com.op.des.web.domain.Order;
import com.op.des.web.vo.order.OrderInfoVO;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
//@Service
public class WxPayService {
    /**
     * AppId
     */
    @Value("${wechat.pay.appId}")
    public String appId;

    /**
     * 商户号
     */
    @Value("${wechat.pay.mchId}")
    public String mchId;

    /**
     * 支付回调地址
     */
    @Value("${wechat.callback.notifyUrl}")
    private String notifyUrl;

    @Autowired
    private WxPayConfig wxPayConfig;


    private static final Long _5MIN = 5 * 60 * 1000L;

    /**
     * 创建订单 - wechat - 生成预支付订单code_url
     *
     * @return
     */
    @Transactional
    public OrderInfoVO createOrder() {
        // 创建订单
        Order order = new Order();
        /**
         * 1、订单id
         * 2、用户ID
         * 3、创建时间
         * 4、更新时间
         * 5、订单信息
         * 6、订单金额
         * 7、状态：支付、初始态、支付中
         * 8、
         */
        // 使用自动更新平台证书的RSA配置
        Config config = wxPayConfig.getConfig();

        // 构建service
        NativePayService service = new NativePayService.Builder().config(config).build();
        // 设置所需参数
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(order.getShouldAmount());  // 微信支付用分来结算  记得 * 100
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(mchId);
        request.setDescription("咨询费");
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(String.valueOf(order.getId()));
        // 微信二维码失效时间 4 分钟
        Date date = new Date(new Date().getTime() + 4 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String expire = format.format(date);
        request.setTimeExpire(expire);
        PrepayResponse response = null;
        try {
            // 调用下单方法，得到应答
            response = service.prepay(request);
            // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
            System.out.println(response.getCodeUrl());
            log.info("CreateOrder-PrePay code_url:{}", response.getCodeUrl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // 设置返回订单信息
        OrderInfoVO orderCreateVo = new OrderInfoVO();
        orderCreateVo.setCodeUrl(response.getCodeUrl());
        orderCreateVo.setOrderCode(order.getId() + "");
        orderCreateVo.setCompanyName(""); //TODO 待提供
        orderCreateVo.setShouldAmount(order.getShouldAmount() + "");
        orderCreateVo.setExpireTime(order.getCreateTime() + _5MIN);
        return orderCreateVo;
    }


}
