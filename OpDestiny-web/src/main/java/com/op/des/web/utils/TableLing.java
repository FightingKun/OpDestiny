package com.op.des.web.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableLing {

    private static Map<Integer, LingItem> table = new HashMap<>();

    static {
        LingItem one = new LingItem();
        one.setDiZhi("寅");
        one.setJieQi(new String[]{"立春", "雨水"});
        List<LingItem.DayItem> parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("戊", 7));
        parts.add(new LingItem.DayItem("丙", 7));
        parts.add(new LingItem.DayItem("甲", 16));
        one.setPartitions(parts);
        table.put(1, one);

        LingItem two = new LingItem();
        two.setDiZhi("卯");
        two.setJieQi(new String[]{"惊蛰", "春分"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("甲", 10));
        parts.add(new LingItem.DayItem("乙", 20));
        two.setPartitions(parts);
        table.put(2, two);

        LingItem three = new LingItem();
        three.setDiZhi("辰");
        three.setJieQi(new String[]{"清明", "谷雨"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("乙", 9));
        parts.add(new LingItem.DayItem("癸", 3));
        parts.add(new LingItem.DayItem("戊", 18));
        three.setPartitions(parts);
        table.put(3, three);

        LingItem four = new LingItem();
        four.setDiZhi("巳");
        four.setJieQi(new String[]{"立夏", "小满"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("戊", 5));
        parts.add(new LingItem.DayItem("庚", 9));
        parts.add(new LingItem.DayItem("丙", 16));
        four.setPartitions(parts);
        table.put(4, four);

        LingItem five = new LingItem();
        five.setDiZhi("午");
        five.setJieQi(new String[]{"芒种", "夏至"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("丙", 10));
        parts.add(new LingItem.DayItem("己", 10));
        parts.add(new LingItem.DayItem("丁", 10));
        five.setPartitions(parts);
        table.put(5, five);

        LingItem six = new LingItem();
        six.setDiZhi("未");
        six.setJieQi(new String[]{"小暑", "大暑"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("丁", 9));
        parts.add(new LingItem.DayItem("乙", 3));
        parts.add(new LingItem.DayItem("己", 18));
        six.setPartitions(parts);
        table.put(6, six);

        LingItem seven = new LingItem();
        seven.setDiZhi("申");
        seven.setJieQi(new String[]{"立秋", "处暑"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("己", 7));
        parts.add(new LingItem.DayItem("戊", 3));
        parts.add(new LingItem.DayItem("任", 3));
        parts.add(new LingItem.DayItem("庚", 17));
        seven.setPartitions(parts);
        table.put(7, seven);

        LingItem eight = new LingItem();
        eight.setDiZhi("酉");
        eight.setJieQi(new String[]{"白露", "秋分"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("庚", 10));
        parts.add(new LingItem.DayItem("辛", 20));
        eight.setPartitions(parts);
        table.put(8, eight);

        LingItem nine = new LingItem();
        nine.setDiZhi("戌");
        nine.setJieQi(new String[]{"寒露", "霜降"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("辛", 9));
        parts.add(new LingItem.DayItem("丁", 3));
        parts.add(new LingItem.DayItem("戊", 18));
        nine.setPartitions(parts);
        table.put(9, nine);

        LingItem ten = new LingItem();
        ten.setDiZhi("亥");
        ten.setJieQi(new String[]{"立冬", "小雪"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("戊", 9));
        parts.add(new LingItem.DayItem("甲", 3));
        parts.add(new LingItem.DayItem("壬", 18));
        ten.setPartitions(parts);
        table.put(10, ten);

        LingItem eleven = new LingItem();
        eleven.setDiZhi("子");
        eleven.setJieQi(new String[]{"大雪", "冬至"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("壬", 10));
        parts.add(new LingItem.DayItem("癸", 20));
        eleven.setPartitions(parts);
        table.put(11, eleven);

        LingItem twelve = new LingItem();
        twelve.setDiZhi("子");
        twelve.setJieQi(new String[]{"小寒", "大寒"});
        parts = new ArrayList<>();
        parts.add(new LingItem.DayItem("癸", 20));
        parts.add(new LingItem.DayItem("辛", 10));
        parts.add(new LingItem.DayItem("己", 10));
        twelve.setPartitions(parts);
        table.put(12, twelve);
    }

    public static String getLingByBirthday(int month, int day) {
        LingItem lingItem = table.get(month);
        List<LingItem.DayItem> partitions = lingItem.getPartitions();
        int sum = 0;
        for (LingItem.DayItem item : partitions) {
            sum = item.getDays() + sum;
            if (day <= sum) {
                return item.getTianGan();
            }
        }
        return null;
    }

    @Data
    static class LingItem {
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        static class DayItem {
            private String tianGan;
            private int days;
        }

        private String diZhi;

        private String[] jieQi;

        private List<DayItem> partitions;
    }
}
