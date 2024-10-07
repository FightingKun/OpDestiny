package com.op.des.web;

import com.op.des.web.dao.mapper.*;
import com.op.des.web.dao.po.*;
import com.op.des.web.param.SpecialReq;
import com.op.des.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 专项分析，妻、财、子、禄、寿
 */
@RestController()
@RequestMapping("/op/des/special")
public class SpecialController {
    @Autowired
    RiZhuLunPOMapper riZhuLunPOMapper;
    @Autowired
    GongWeiLunPOMapper gongWeiLunPOMapper;
    @Autowired
    XingShenLunPOMapper xingShenLunPOMapper;
    @Autowired
    ShenShaLunPOMapper shenShaLunPOMapper;
    @Autowired
    QuJiLunPOMapper quJiLunPOMapper;

    @ResponseBody
    @RequestMapping("/lun")
    public SpecialQiVO specialYinYuan(SpecialReq req) {
        SpecialQiVO specialQiVO = new SpecialQiVO();
        //日柱
        RiZhuLunPOCriteria example = new RiZhuLunPOCriteria();
        RiZhuLunPOCriteria.Criteria criteria = example.createCriteria();
        criteria.andRizhuEqualTo(req.getRiZhu());
        criteria.andSexEqualTo(req.getSex());
        criteria.andSpecialNameEqualTo(req.getSpecialName());
        List<RiZhuLunPO> riZhuLunPOS = riZhuLunPOMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(riZhuLunPOS)) {
            RiZhuLunPO riZhuLunPO = riZhuLunPOS.get(0);
            specialQiVO.setRiZhu(new SpecialQiVO.YinYuanVO(riZhuLunPO.getIntroduction()));
        }
        //宫位
        GongWeiLunPOCriteria gongWeiLunPOCriteria = new GongWeiLunPOCriteria();
        GongWeiLunPOCriteria.Criteria criteria1 = gongWeiLunPOCriteria.createCriteria();
        criteria1.andLocationEqualTo(req.getLocation());
        criteria1.andGanzhiEqualTo(req.getGanZhi());
        criteria1.andSexEqualTo(req.getSex());
        criteria1.andSpecialNameEqualTo(req.getSpecialName());
        List<GongWeiLunPO> gongWeiLunPOS = gongWeiLunPOMapper.selectByExample(gongWeiLunPOCriteria);
        if (!CollectionUtils.isEmpty(gongWeiLunPOS)) {
            GongWeiLunPO gongWeiLunPO = gongWeiLunPOS.get(0);
            specialQiVO.setGongWei(new SpecialQiVO.YinYuanVO(gongWeiLunPO.getIntroduction()));
        }
        //星神
        XingShenLunPOCriteria xingShenLunPOCriteria = new XingShenLunPOCriteria();
        XingShenLunPOCriteria.Criteria criteria2 = xingShenLunPOCriteria.createCriteria();
        criteria2.andLocationEqualTo(req.getLocation());
        criteria2.andGanzhiEqualTo(req.getGanZhi());
        criteria2.andSexEqualTo(req.getSex());
        criteria2.andSpecialNameEqualTo(req.getSpecialName());
        List<XingShenLunPO> xingShenLunPOS = xingShenLunPOMapper.selectByExample(xingShenLunPOCriteria);
        if (!CollectionUtils.isEmpty(xingShenLunPOS)) {
            XingShenLunPO xingShenLunPO = xingShenLunPOS.get(0);
            specialQiVO.setXingShen(new SpecialQiVO.YinYuanVO(xingShenLunPO.getIntroduction()));
        }
        //神煞
        ShenShaLunPOCriteria shenShaLunPOCriteria = new ShenShaLunPOCriteria();
        ShenShaLunPOCriteria.Criteria criteria3 = shenShaLunPOCriteria.createCriteria();
        criteria3.andShenshaEqualTo(req.getShenSha());
        criteria3.andSexEqualTo(req.getSex());
        criteria3.andSpecialNameEqualTo(req.getSpecialName());
        List<ShenShaLunPO> shenShaLunPOS = shenShaLunPOMapper.selectByExample(shenShaLunPOCriteria);
        if (!CollectionUtils.isEmpty(shenShaLunPOS)) {
            ShenShaLunPO shenShaLunPO = shenShaLunPOS.get(0);
            specialQiVO.setShenSha(new SpecialQiVO.YinYuanVO(shenShaLunPO.getIntroduction()));
        }
        //趋吉
        QuJiLunPOCriteria quJiLunPOCriteria = new QuJiLunPOCriteria();
        QuJiLunPOCriteria.Criteria criteria4 = quJiLunPOCriteria.createCriteria();
        criteria4.andTianganEqualTo(req.getTianGan());
        criteria4.andSexEqualTo(req.getSex());
        criteria4.andSpecialNameEqualTo(req.getSpecialName());
        List<QuJiLunPO> quJiLunPOS = quJiLunPOMapper.selectByExample(quJiLunPOCriteria);
        if (!CollectionUtils.isEmpty(quJiLunPOS)) {
            QuJiLunPO quJiLunPO = quJiLunPOS.get(0);
            specialQiVO.setQuJi(new SpecialQiVO.YinYuanVO(quJiLunPO.getIntroduction()));
        }
        return specialQiVO;

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
