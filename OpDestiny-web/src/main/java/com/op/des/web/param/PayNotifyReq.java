package com.op.des.web.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayNotifyReq {
    private String id;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("resource_type")
    private String resourceType;
    private String resource;
    private String summary;

    @Data
    static class ResourceData {
        private String appid;
        private String mchid;
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        @JsonProperty("transaction_id")
        private String transactionId;
        @JsonProperty("trade_type")
        private String tradeType;
        @JsonProperty("trade_state")
        private String tradeState;
        @JsonProperty("trade_state_desc")
        private String tradeStateDesc;
        @JsonProperty("success_time")
        private String successTime;
    }
}
