package com.op.des.web.service.wxpay;

import com.op.des.web.dao.mapper.OrderInfoPOMapper;
import com.op.des.web.dao.po.OrderInfoPO;
import com.op.des.web.dao.po.OrderInfoPOCriteria;
import com.op.des.web.param.CreateOrderReq;
import com.op.des.web.vo.order.OrderInfoVO;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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

    @Autowired
    private OrderInfoPOMapper orderInfoPOMapper;


    private static final Long _5MIN = 5 * 60 * 1000L;

    /**
     * 创建订单 - wechat - 生成预支付订单code_url
     *
     * @return
     */
    public OrderInfoVO createOrder(CreateOrderReq req, Long userId, String username, String phone) {
        // 创建订单
        OrderInfoPO order = new OrderInfoPO();
        order.setUserId(userId);
        order.setUsername(username);
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        order.setPhone(phone);
        order.setAmount(req.getAmount());
        order.setContent("");
        order.setStatus((byte) 0);//状态：0:初始态、1：支付中，2：支付成功，3：支付失败
        orderInfoPOMapper.insertSelective(order);

        // 使用自动更新平台证书的RSA配置
        Config config = wxPayConfig.getConfig();
        // 构建service
        NativePayService service = new NativePayService.Builder().config(config).build();
        // 设置所需参数
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(order.getAmount().intValue());  // 微信支付用分来结算  记得 * 100
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(mchId);
        request.setDescription("咨询费");
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(String.valueOf(order.getId()));
        // 微信二维码失效时间 4 分钟
        Date date = new Date(new Date().getTime() + 10 * 60 * 1000);
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
        orderCreateVo.setShouldAmount(order.getAmount() + "");
        orderCreateVo.setExpireTime(order.getCreateTime() + _5MIN);
        return orderCreateVo;
    }

    /**
     * 微信支付回调接口
     *
     * @param request
     * @param response
     * @return
     */
    public String wxpayNotify(JSONObject body, HttpServletRequest request, HttpServletResponse response) {
        log.info("微信支付回调：body:{}, request:{}, response:{}", body, request, response);
        StringBuilder requestBody = null;
        JSONObject json = new JSONObject();
        // 获取请求体原内容（此时获取的数据是加密的）
        try (BufferedReader reader = request.getReader();) {
            requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            log.info("微信支付回调-requestBody:{}", requestBody);
        } catch (Exception e) {
            log.info("微信支付回调-处理失败:{}", requestBody);
            response.setStatus(400);
            json.put("code", "FAIL");
            json.put("message", "验签失败");
        }

        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(request.getHeader("wechatPay-Serial"))     //证书序列号
                .nonce(request.getHeader("wechatPay-Nonce"))             // 随机字符串
                .signature(request.getHeader("wechatPay-Signature"))     // 签名
                .timestamp(request.getHeader("wechatPay-Timestamp"))     // 时间戳
                .signType(request.getHeader("wechatPay-Signature-Type")) // 签名类型
                .body(requestBody.toString())                               // 请求体内容（原始内容，不要解析）
                .build();
        log.info("微信支付回调-requestData：requestParam:{}", requestParam);


        //获取微信证书，没有的话重新构造一个
        Config config = wxPayConfig.getConfig();
        NotificationParser parser = new NotificationParser((NotificationConfig) config);
        // 以支付通知回调为例，验签、解密并转换成 Transaction
        Transaction transaction = parser.parse(requestParam, Transaction.class);
        // 获取支付单号
        log.info("支付成功，回调信息：{}", transaction);
        // 打印其中的"微信支付单号"字段，可用于查单操作
        log.info("微信支付单号：{}", transaction.getTransactionId());
        // 获取微信支付回调状态
        Transaction.TradeStateEnum tradeState = transaction.getTradeState();
        log.info("微信Native支付回调状态 tradeState:{}", tradeState);

        // 返回给微信回调信息成功与否
        if (tradeState == Transaction.TradeStateEnum.SUCCESS) {
            // 业务逻辑代码

            // 将微信返回的订单号参数信息传到Dao层，进行校验
            OrderInfoPO order = orderInfoPOMapper.selectByPrimaryKey(Long.parseLong(transaction.getOutTradeNo()));
            log.info("微信支付回调-Order：order:{}", order);
            // 订单待支付状态 - 更新订单信息
            if (order.getStatus().equals((byte) 1)) {
                OrderInfoPO orderInfoPO = new OrderInfoPO();
                orderInfoPO.setStatus((byte) 2);
                orderInfoPO.setUpdateTime(System.currentTimeMillis());
                OrderInfoPOCriteria example = new OrderInfoPOCriteria();
                OrderInfoPOCriteria.Criteria criteria = example.createCriteria();
                criteria.andIdEqualTo(Long.parseLong(transaction.getOutTradeNo()));
                criteria.andStatusEqualTo((byte) 1);
                orderInfoPOMapper.updateByExampleSelective(orderInfoPO, example);
            }
            response.setStatus(200);
            json.put("code", "SUCCESS");
            json.put("message", "操作成功");
        } else {
            response.setStatus(400);
            json.put("code", "FAIL");
            json.put("message", "验签失败");
        }
        return json.toString();
    }
}
