package com.op.des.web.domain;

import java.util.HashMap;
import java.util.Map;

public class ShiShen {

    private static Map<String, String> yongShenTable = new HashMap() {
        {
            put("正官弱财星", "比劫、印星");
            put("正官弱食伤", "印星");
            put("正官弱官杀", "印星");

            put("正官强比劫、印星", "官星");
            put("正官强印星", "财星");
            put("正官强食伤", "财星");

            put("七杀弱财星、印星", "官星");
            put("七杀弱食伤", "财星");
            put("七杀弱官杀", "财星");

            put("七杀强比劫", "七杀");
            put("七杀强印星", "财星");
            put("七杀强食伤", "食伤");

            put("财星弱食伤", "印星");
            put("财星弱财星", "比劫");
            put("财星弱官杀", "财星");

            put("财星格强比劫", "食伤、官杀");
            put("财星格强印星", "财星");

            put("印星弱官杀", "印星");
            put("印星弱食伤", "印星");
            put("印星弱财星", "比劫");

            put("印星强比劫", "官杀、食伤");
            put("印星强印星", "财星");
            put("印星强财星", "食伤、财星");

            put("食神弱官杀", "印星");
            put("食神弱食伤", "印星");
            put("食神弱财星", "比劫");

            put("食神强比劫", "食神");
            put("食神强印星", "财星");
            put("食神强财星", "官杀");

            put("伤官弱财星", "比劫");
            put("伤官弱官杀", "印星");
            put("伤官弱食伤", "印星");

            put("伤官强比劫", "官杀");
            put("伤官强印星", "财星");
        }
    };

//    private static Map<String >

    public static String quYongShen(String key) {
        return yongShenTable.get(key);
    }


}
