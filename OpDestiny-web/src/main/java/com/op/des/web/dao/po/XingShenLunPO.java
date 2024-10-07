package com.op.des.web.dao.po;

import java.io.Serializable;

public class XingShenLunPO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.id
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.location
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private String location;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.ganzhi
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private String ganzhi;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.sex
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.special_name
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private String specialName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xingshenlun.introduction
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private String introduction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xingshenlun
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.id
     *
     * @return the value of xingshenlun.id
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.id
     *
     * @param id the value for xingshenlun.id
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.location
     *
     * @return the value of xingshenlun.location
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.location
     *
     * @param location the value for xingshenlun.location
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.ganzhi
     *
     * @return the value of xingshenlun.ganzhi
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public String getGanzhi() {
        return ganzhi;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.ganzhi
     *
     * @param ganzhi the value for xingshenlun.ganzhi
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setGanzhi(String ganzhi) {
        this.ganzhi = ganzhi == null ? null : ganzhi.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.sex
     *
     * @return the value of xingshenlun.sex
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.sex
     *
     * @param sex the value for xingshenlun.sex
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.special_name
     *
     * @return the value of xingshenlun.special_name
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public String getSpecialName() {
        return specialName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.special_name
     *
     * @param specialName the value for xingshenlun.special_name
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setSpecialName(String specialName) {
        this.specialName = specialName == null ? null : specialName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xingshenlun.introduction
     *
     * @return the value of xingshenlun.introduction
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xingshenlun.introduction
     *
     * @param introduction the value for xingshenlun.introduction
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xingshenlun
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", location=").append(location);
        sb.append(", ganzhi=").append(ganzhi);
        sb.append(", sex=").append(sex);
        sb.append(", specialName=").append(specialName);
        sb.append(", introduction=").append(introduction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xingshenlun
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
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
        XingShenLunPO other = (XingShenLunPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getGanzhi() == null ? other.getGanzhi() == null : this.getGanzhi().equals(other.getGanzhi()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getSpecialName() == null ? other.getSpecialName() == null : this.getSpecialName().equals(other.getSpecialName()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xingshenlun
     *
     * @mbg.generated Mon Oct 07 11:38:08 CST 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getGanzhi() == null) ? 0 : getGanzhi().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getSpecialName() == null) ? 0 : getSpecialName().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        return result;
    }
}