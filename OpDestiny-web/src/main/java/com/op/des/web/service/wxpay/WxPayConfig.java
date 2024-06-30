package com.op.des.web.service.wxpay;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Scanner;

@Slf4j
@Data
@Component
public class WxPayConfig {

    private static volatile Config config;

    /**
     * 商户号
     */
    @Value("${wechat.pay.mchId}")
    public String mchId;

    /**
     * 商户API私钥
     */
    @Value("${wechat.pay.privateKey}")
    public String privateKey;

    /**
     * 商户证书序列号
     */
    @Value("${wechat.pay.merchantSerialNumber}")
    public String merchantSerialNumber;

    /**
     * 商户APIV3密钥
     */
    @Value("${wechat.pay.apiV3key}")
    public String apiV3key;


    //SDK 提供的定时更新平台证书
    public Config getConfig() {
        if (config == null) {
            config = new RSAAutoCertificateConfig.Builder()
                    .merchantId(mchId)
                    .privateKey(new String(Base64.getDecoder().decode(privateKey)))
                    .merchantSerialNumber(merchantSerialNumber)
                    .apiV3Key(new String(Base64.getDecoder().decode(apiV3key)))
                    .build();
        }
        return config;
    }
}
