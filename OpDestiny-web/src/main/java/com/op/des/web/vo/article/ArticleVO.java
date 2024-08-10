package com.op.des.web.vo.article;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {
    ItemVO hot;
    List<ItemVO> articles;

    @Data
    public static class ItemVO {
        Long id;
        byte[] image;
        String title;
        String content;
        String introduction;
        int ord;
    }
}
