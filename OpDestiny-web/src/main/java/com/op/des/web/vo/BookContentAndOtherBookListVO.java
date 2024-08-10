package com.op.des.web.vo;

import com.op.des.web.dao.po.BookInfoPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookContentAndOtherBookListVO {
    BookInfoVO bookInfoVO;
    List<BookInfoPO> others;
}
