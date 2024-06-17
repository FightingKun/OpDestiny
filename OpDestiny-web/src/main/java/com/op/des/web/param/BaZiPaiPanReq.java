package com.op.des.web.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BaZiPaiPanReq {
    private String name;
    /**
     * 0：男，1：女
     */
    private int sex;
    /**
     * 是否使用太阳时,1:使用，0：不使用
     */
    private int useSunTime;

    /**
     * 出生地点-省份
     */
    private String birthAddressProvince;
    /**
     * 出生地点-城市
     */
    private String birthAddressCity;
    /**
     * 0:阳历，1：阴历
     */
    private int timeType;
    /**
     * 出生年
     */
    private int year;
    /**
     * 出生月
     */
    private int month;
    /**
     * 出生日
     */
    private int day;
    /**
     * 出生时
     */
    private int hour;

    public String getStringSex() {
        return sex == 0 ? "男" : "女";
    }

}
