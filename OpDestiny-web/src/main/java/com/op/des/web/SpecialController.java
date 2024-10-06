package com.op.des.web;

import com.op.des.web.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专项分析，妻、财、子、禄、寿
 */
@RestController("/op/des/special")
public class SpecialController {


    @RequestMapping("/yinYuan")
    public SpecialQiVO specialYinYuan() {

        return new SpecialQiVO();

    }


    @RequestMapping("/work")
    public SpecialWorkVO specialWork() {

        return new SpecialWorkVO();

    }

    @RequestMapping("/zi")
    public SpecialZiVO specialZi() {

        return new SpecialZiVO();

    }

    @RequestMapping("/lu")
    public SpecialLuVO specialLu() {

        return new SpecialLuVO();

    }

    @RequestMapping("/shou")
    public SpecialShouVO specialShou() {

        return new SpecialShouVO();

    }


}
