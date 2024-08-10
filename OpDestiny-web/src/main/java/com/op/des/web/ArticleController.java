package com.op.des.web;

import com.op.des.web.dao.mapper.ArticlePOMapper;
import com.op.des.web.dao.po.ArticlePO;
import com.op.des.web.dao.po.ArticlePOCriteria;
import com.op.des.web.param.ArticleReq;
import com.op.des.web.vo.article.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/op/des/article")
public class ArticleController {

    @Autowired
    ArticlePOMapper mapper;

    @ResponseBody
    @RequestMapping("/query")
    public ArticleVO query(ArticleReq articleReq) {

        ArticlePOCriteria example = new ArticlePOCriteria();
        example.setOffsetNum(articleReq.getOffset());
        example.setPageSize(articleReq.getPageSize());
        List<ArticlePO> articlePOS = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(articlePOS)) {
            return new ArticleVO();
        }
        List<ArticlePO> collect = articlePOS.stream().sorted(Comparator.comparing(ArticlePO::getOrd)).collect(Collectors.toList());
        ArticleVO articleVO = new ArticleVO();
        ArticleVO.ItemVO itemVO = new ArticleVO.ItemVO();
        ArticlePO articlePOWithBLOB = collect.get(0);
        itemVO.setTitle(articlePOWithBLOB.getTitle());
        itemVO.setOrd(articlePOWithBLOB.getOrd());
        itemVO.setId(articlePOWithBLOB.getId());
        itemVO.setIntroduction(articlePOWithBLOB.getIntroduction());
        itemVO.setImage(articlePOWithBLOB.getImage());
        articleVO.setHot(itemVO);

        //所有的文章
        List<ArticleVO.ItemVO> allArticles = new ArrayList<>();
        collect.forEach(articlePO -> {
            ArticleVO.ItemVO item = new ArticleVO.ItemVO();
            item.setTitle(articlePO.getTitle());
            item.setIntroduction(articlePO.getIntroduction());
            item.setImage(articlePO.getImage());
            item.setOrd(articlePO.getOrd());
            item.setId(articlePO.getId());
            allArticles.add(item);
        });
        articleVO.setArticles(allArticles);
        return articleVO;
    }
}
