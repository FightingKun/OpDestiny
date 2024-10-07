package com.op.des.web;

import com.op.des.web.dao.mapper.*;
import com.op.des.web.dao.po.*;
import com.op.des.web.param.SpecialManagerReq;
import com.op.des.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端
 * 专项分析，妻、财、子、禄、寿
 */
@RestController
@RequestMapping("/op/des/special/manager/")
public class SpecialManagerController {

    @Autowired
    RiZhuLunPOMapper mapper;
    @Autowired
    GongWeiLunPOMapper gongWeiLunPOMapper;
    @Autowired
    XingShenLunPOMapper xingShenLunPOMapper;
    @Autowired
    ShenShaLunPOMapper shenShaLunPOMapper;
    @Autowired
    QuJiLunPOMapper quJiLunPOMapper;
    @ResponseBody
    @RequestMapping("/addrizhu")
    public SpecialManagerVO addRiZhuLun(SpecialManagerReq req) {
        RiZhuLunPO riZhuLunPO = new RiZhuLunPO();
        riZhuLunPO.setSpecialName(req.getSpecialName());
        riZhuLunPO.setIntroduction(req.getIntroduction());
        riZhuLunPO.setRizhu(req.getRiZhu());
        riZhuLunPO.setSex(req.getSex());
        try {
            mapper.insertSelective(riZhuLunPO);
        } catch (Exception e) {
            return SpecialManagerVO.fail();
        }
        return SpecialManagerVO.success();
    }

    @ResponseBody
    @RequestMapping("/addgongwei")
    public SpecialManagerVO addGongWeiLun(SpecialManagerReq req) {
        GongWeiLunPO gongWeiLunPO = new GongWeiLunPO();
        gongWeiLunPO.setLocation(req.getLocation());
        gongWeiLunPO.setGanzhi(req.getGanZhi());
        gongWeiLunPO.setSpecialName(req.getSpecialName());
        gongWeiLunPO.setIntroduction(req.getIntroduction());
        gongWeiLunPO.setSex(req.getSex());
        try {
            gongWeiLunPOMapper.insertSelective(gongWeiLunPO);
        } catch (Exception e) {
            return SpecialManagerVO.fail();
        }
        return SpecialManagerVO.success();
    }
    @ResponseBody
    @RequestMapping("/addxingshen")
    public SpecialManagerVO addXingShenLun(SpecialManagerReq req) {
        XingShenLunPO xingShenLunPO = new XingShenLunPO();
        xingShenLunPO.setLocation(req.getLocation());
        xingShenLunPO.setGanzhi(req.getGanZhi());
        xingShenLunPO.setSpecialName(req.getSpecialName());
        xingShenLunPO.setIntroduction(req.getIntroduction());
        xingShenLunPO.setSex(req.getSex());
        try {
            xingShenLunPOMapper.insertSelective(xingShenLunPO);
        } catch (Exception e) {
            return SpecialManagerVO.fail();
        }
        return SpecialManagerVO.success();
    }

    @ResponseBody
    @RequestMapping("/addshensha")
    public SpecialManagerVO addShenShaLun(SpecialManagerReq req) {
        ShenShaLunPO shenShaLunPO = new ShenShaLunPO();
        shenShaLunPO.setIntroduction(req.getIntroduction());
        shenShaLunPO.setSex(req.getSex());
        shenShaLunPO.setSpecialName(req.getSpecialName());
        shenShaLunPO.setShensha(req.getShenSha());
        try {
            shenShaLunPOMapper.insertSelective(shenShaLunPO);
        } catch (Exception e) {
            return SpecialManagerVO.fail();
        }
        return SpecialManagerVO.success();
    }
    @ResponseBody
    @RequestMapping("/addquji")
    public SpecialManagerVO addQuJiLun(SpecialManagerReq req) {
        QuJiLunPO quJiLunPO = new QuJiLunPO();
        quJiLunPO.setTiangan(req.getTianGan());
        quJiLunPO.setSpecialName(req.getSpecialName());
        quJiLunPO.setIntroduction(req.getIntroduction());
        quJiLunPO.setSex(req.getSex());
        try {
            quJiLunPOMapper.insertSelective(quJiLunPO);
        } catch (Exception e) {
            return SpecialManagerVO.fail();
        }
        return SpecialManagerVO.success();
    }


}
