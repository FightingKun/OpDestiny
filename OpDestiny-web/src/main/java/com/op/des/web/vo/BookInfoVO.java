package com.op.des.web.vo;

import lombok.Data;

@Data
public class BookInfoVO {
    private String content;
    private String icon;
    private String detail;
    private String name;
    private Long id;

    private byte[] imageLogo;
}
