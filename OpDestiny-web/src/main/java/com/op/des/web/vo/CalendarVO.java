package com.op.des.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日历
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarVO {
    private String  titleDate;

    private String day;

    private String weekDay;

    private String ganzhiDay;

}
