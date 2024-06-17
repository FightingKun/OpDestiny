package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 日历
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HuangLiVO implements Serializable {
    private String  titleDate;

    private int day;

    private String weekDay;

    private List<String> ganzhi;

}
