package com.op.des.web.dao.po;

import java.io.Serializable;

public class RiZhuLunPO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rizhulun.id
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rizhulun.rizhu
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private String rizhu;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rizhulun.sex
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rizhulun.special_name
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private String specialName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rizhulun.introduction
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private String introduction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rizhulun
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rizhulun.id
     *
     * @return the value of rizhulun.id
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rizhulun.id
     *
     * @param id the value for rizhulun.id
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rizhulun.rizhu
     *
     * @return the value of rizhulun.rizhu
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public String getRizhu() {
        return rizhu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rizhulun.rizhu
     *
     * @param rizhu the value for rizhulun.rizhu
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public void setRizhu(String rizhu) {
        this.rizhu = rizhu == null ? null : rizhu.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rizhulun.sex
     *
     * @return the value of rizhulun.sex
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rizhulun.sex
     *
     * @param sex the value for rizhulun.sex
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rizhulun.special_name
     *
     * @return the value of rizhulun.special_name
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public String getSpecialName() {
        return specialName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rizhulun.special_name
     *
     * @param specialName the value for rizhulun.special_name
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public void setSpecialName(String specialName) {
        this.specialName = specialName == null ? null : specialName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rizhulun.introduction
     *
     * @return the value of rizhulun.introduction
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rizhulun.introduction
     *
     * @param introduction the value for rizhulun.introduction
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhulun
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rizhu=").append(rizhu);
        sb.append(", sex=").append(sex);
        sb.append(", specialName=").append(specialName);
        sb.append(", introduction=").append(introduction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhulun
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RiZhuLunPO other = (RiZhuLunPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRizhu() == null ? other.getRizhu() == null : this.getRizhu().equals(other.getRizhu()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getSpecialName() == null ? other.getSpecialName() == null : this.getSpecialName().equals(other.getSpecialName()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhulun
     *
     * @mbg.generated Mon Oct 07 11:14:20 CST 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRizhu() == null) ? 0 : getRizhu().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getSpecialName() == null) ? 0 : getSpecialName().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        return result;
    }
}