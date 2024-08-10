package com.op.des.web.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ArticleReq {
    int pageSize;
    int pageNum;

    @JsonIgnore
    public int getOffset() {
        if (pageSize == 0) {
            pageSize = 10;
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        return (pageNum - 1) * pageSize;
    }
}
