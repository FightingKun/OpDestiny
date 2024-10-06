package com.op.des.web.domain;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaGuaWuXing {

    public static Gua getBaGua(String wuxing) {
        return baGuaWuXing.get(wuxing);
    }


    private static Map<String, Gua> baGuaWuXing = new HashMap() {
        {
            put("水", new Gua(new ZhaiGua("东四宅", Lists.newArrayList("坎宅")),
                    Lists.newArrayList("一白"),
                    Lists.newArrayList("黑色"),
                    Lists.newArrayList("北方", "东北方"),
                    Lists.newArrayList("猪", "鼠"),
                    Lists.newArrayList("1", "6"),
                    Lists.newArrayList("冬"),
                    Lists.newArrayList("1", "6"),
                    Lists.newArrayList("玄武"),
                    Lists.newArrayList("波浪形"),
                    Lists.newArrayList("羽")));
            put("火", new Gua(new ZhaiGua("东四宅", Lists.newArrayList("离宅")),
                    Lists.newArrayList("九紫"),
                    Lists.newArrayList("红色"),
                    Lists.newArrayList("南方", "西南方"),
                    Lists.newArrayList("蛇", "马"),
                    Lists.newArrayList("1", "6"),
                    Lists.newArrayList("夏"),
                    Lists.newArrayList("2", "7"),
                    Lists.newArrayList("朱雀"),
                    Lists.newArrayList("三角形"),
                    Lists.newArrayList("征")));
            put("木", new Gua(new ZhaiGua("东四宅", Lists.newArrayList("震宅", "巽宅")),
                    Lists.newArrayList("三碧", "四绿"),
                    Lists.newArrayList("绿色"),
                    Lists.newArrayList("东方", "东南方"),
                    Lists.newArrayList("虎", "兔"),
                    Lists.newArrayList("3", "8"),
                    Lists.newArrayList("春"),
                    Lists.newArrayList("3", "8"),
                    Lists.newArrayList("青龙"),
                    Lists.newArrayList("长方形"),
                    Lists.newArrayList("角")));

            put("金", new Gua(new ZhaiGua("西四宅", Lists.newArrayList("兑宅", "乾宅")),
                    Lists.newArrayList("七赤", "六白"),
                    Lists.newArrayList("白色"),
                    Lists.newArrayList("西方", "西北方"),
                    Lists.newArrayList("猴", "鸡"),
                    Lists.newArrayList("4", "9"),
                    Lists.newArrayList("秋"),
                    Lists.newArrayList("4", "9"),
                    Lists.newArrayList("白虎"),
                    Lists.newArrayList("圆形"),
                    Lists.newArrayList("商")));
            put("土", new Gua(new ZhaiGua("西四宅", Lists.newArrayList("坤宅", "艮宅")),
                    Lists.newArrayList("五黄", "八白"),
                    Lists.newArrayList("中黄"),
                    Lists.newArrayList("中"),
                    Lists.newArrayList("龙", "狗", "牛", "羊"),
                    Lists.newArrayList("5", "10"),
                    Lists.newArrayList("四季四节"),
                    Lists.newArrayList("5", "10"),
                    Lists.newArrayList("勾陈"),
                    Lists.newArrayList("正方形"),
                    Lists.newArrayList("宫")));
        }
    };

    @Data
    @AllArgsConstructor
    public static class Gua {
        ZhaiGua zhaiGua;
        List<String> jiuXing;
        List<String> colors;
        List<String> locations;
        List<String> shengxiaos;
        List<String> nums;
        List<String> seasons;
        List<String> louCengs;
        List<String> shenShous;
        List<String> shapes;
        List<String> wuYins;

    }

    @Data
    @AllArgsConstructor
    public static class ZhaiGua {
        private String name;
        private List<String> zhaiName;
    }
}
