package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDetailVO implements Serializable {

    //命主
    private String userName;
    //性别
    private int sex;
    //城市
    private String addressCity;
    //经度
    private String location;
    //属相
    private String shuxiang;
    //星座
    private String xingzuo;

    //出生的节气
    private List<String> jieQis;

    //得令
    private String deling;

    private List<String> siZhuWuXing;

    //从八字提要中获取
    private List<String> baZiTiyao;
}
