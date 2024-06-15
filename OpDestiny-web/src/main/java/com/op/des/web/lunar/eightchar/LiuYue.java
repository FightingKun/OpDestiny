package com.op.des.web.lunar.eightchar;


import com.google.common.collect.Lists;
import com.op.des.web.lunar.EightChar;
import com.op.des.web.lunar.util.LunarUtil;
import com.op.des.web.utils.PaiPanUtils;
import javafx.util.Pair;

import java.util.List;

/**
 * 流月
 *
 * @author 6tail
 */
public class LiuYue {
  /**
   * 序数，0-9
   */
  private final int index;

  private final LiuNian liuNian;

  public LiuYue(LiuNian liuNian, int index) {
    this.liuNian = liuNian;
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public String getShiShenGan(EightChar eightChar) {
    return LunarUtil.SHI_SHEN.get(eightChar.getDayGan() + getGanZhi().substring(0, 1));
  }
  public List<String> getHideGan() {
    return LunarUtil.ZHI_HIDE_GAN.get(getGanZhi().substring(1, 2));
  }

  public String getNaYin(String zhi) {
    return LunarUtil.NAYIN.get(zhi);
  }

  public String getKongWang(EightChar eightChar) {
    String dayXunKong = eightChar.getDayXunKong();
    String zhi = getGanZhi().substring(1,2);
    if (dayXunKong.contains(zhi)) {
      return "日柱空亡";
    }
    String yearXunKong = eightChar.getYearXunKong();
    if (yearXunKong.contains(zhi)) {
      return "年柱空亡";
    }
    return null;
  }
  public List<String> getShenSha(String sex, EightChar eightChar) {
    String gan = getGanZhi().substring(0, 1), zhi = getGanZhi().substring(1, 2);
    List<String> shenShas = Lists.newArrayList();

    //干支神煞
    List<String> dayShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(eightChar.getDayGan() + zhi, Lists.newArrayList());
    List<String> yearShenShas = LunarUtil.ganZhiShenShaTable.getOrDefault(eightChar.getYearGan() + zhi, Lists.newArrayList());
    shenShas.addAll(dayShenShas);
    shenShas.addAll(yearShenShas);

    //天干神煞
    List<String> tianGanShenSha = LunarUtil.tianGanShenShaTable.getOrDefault(eightChar.getDayGan() + zhi, Lists.newArrayList());
    shenShas.addAll(tianGanShenSha);
    //日支神煞
    List<String> dayZhiShenSha = LunarUtil.dayZhiShenShaTable.getOrDefault(eightChar.getDayZhi() + zhi, Lists.newArrayList());
    shenShas.addAll(dayZhiShenSha);
    //月支神煞
    List<String> monthZhiShenSha = LunarUtil.monthZhiShenShaTable.getOrDefault(eightChar.getMonthZhi() + zhi, Lists.newArrayList());
    shenShas.addAll(monthZhiShenSha);
    //年支神煞表
    List<String> yearZhiShenSha = LunarUtil.yearZhiShenShaTable.getOrDefault(eightChar.getYearZhi() + zhi, Lists.newArrayList());
    shenShas.addAll(yearZhiShenSha);

    //其他神煞
    //元辰
    Pair<Boolean, String> yuanChen = PaiPanUtils.isYuanChen(sex, zhi, eightChar);
    if (yuanChen.getKey()) {
      shenShas.add(yuanChen.getValue());
    }
    //勾绞煞
    if (PaiPanUtils.isGouJiaoSha(zhi, sex, eightChar.getYearGan(), eightChar.getYearZhi())) {
      shenShas.add("勾绞煞");
    }

    return shenShas;
  }

  /**
   * 获取中文的月
   *
   * @return 中文月，如正
   */
  public String getMonthInChinese() {
    return LunarUtil.MONTH[index + 1];
  }

  /**
   * 获取干支
   * <p>
   * 《五虎遁》
   * 甲己之年丙作首，
   * 乙庚之年戊为头，
   * 丙辛之年寻庚上，
   * 丁壬壬寅顺水流，
   * 若问戊癸何处走，
   * 甲寅之上好追求。
   *
   * @return 干支
   */
  public String getGanZhi() {
    int offset = 0;
    String yearGan = liuNian.getGanZhi().substring(0, 1);
    if ("甲".equals(yearGan) || "己".equals(yearGan)) {
      offset = 2;
    } else if ("乙".equals(yearGan) || "庚".equals(yearGan)) {
      offset = 4;
    } else if ("丙".equals(yearGan) || "辛".equals(yearGan)) {
      offset = 6;
    } else if ("丁".equals(yearGan) || "壬".equals(yearGan)) {
      offset = 8;
    }
    String gan = LunarUtil.GAN[(index + offset) % 10 + 1];
    String zhi = LunarUtil.ZHI[(index + LunarUtil.BASE_MONTH_ZHI_INDEX) % 12 + 1];
    return gan + zhi;
  }

  /**
   * 获取所在旬
   * @return 旬
   */
  public String getXun(){
    return LunarUtil.getXun(getGanZhi());
  }

  /**
   * 获取旬空(空亡)
   * @return 旬空(空亡)
   */
  public String getXunKong(){
    return LunarUtil.getXunKong(getGanZhi());
  }
}
