package com.op.des.web.dao.po;

import java.util.ArrayList;
import java.util.List;

public class BaZiTiYaoPOCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public BaZiTiYaoPOCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andZhiMonthIsNull() {
            addCriterion("zhi_month is null");
            return (Criteria) this;
        }

        public Criteria andZhiMonthIsNotNull() {
            addCriterion("zhi_month is not null");
            return (Criteria) this;
        }

        public Criteria andZhiMonthEqualTo(String value) {
            addCriterion("zhi_month =", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthNotEqualTo(String value) {
            addCriterion("zhi_month <>", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthGreaterThan(String value) {
            addCriterion("zhi_month >", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthGreaterThanOrEqualTo(String value) {
            addCriterion("zhi_month >=", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthLessThan(String value) {
            addCriterion("zhi_month <", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthLessThanOrEqualTo(String value) {
            addCriterion("zhi_month <=", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthLike(String value) {
            addCriterion("zhi_month like", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthNotLike(String value) {
            addCriterion("zhi_month not like", value, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthIn(List<String> values) {
            addCriterion("zhi_month in", values, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthNotIn(List<String> values) {
            addCriterion("zhi_month not in", values, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthBetween(String value1, String value2) {
            addCriterion("zhi_month between", value1, value2, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiMonthNotBetween(String value1, String value2) {
            addCriterion("zhi_month not between", value1, value2, "zhiMonth");
            return (Criteria) this;
        }

        public Criteria andZhiDayIsNull() {
            addCriterion("zhi_day is null");
            return (Criteria) this;
        }

        public Criteria andZhiDayIsNotNull() {
            addCriterion("zhi_day is not null");
            return (Criteria) this;
        }

        public Criteria andZhiDayEqualTo(String value) {
            addCriterion("zhi_day =", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayNotEqualTo(String value) {
            addCriterion("zhi_day <>", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayGreaterThan(String value) {
            addCriterion("zhi_day >", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayGreaterThanOrEqualTo(String value) {
            addCriterion("zhi_day >=", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayLessThan(String value) {
            addCriterion("zhi_day <", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayLessThanOrEqualTo(String value) {
            addCriterion("zhi_day <=", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayLike(String value) {
            addCriterion("zhi_day like", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayNotLike(String value) {
            addCriterion("zhi_day not like", value, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayIn(List<String> values) {
            addCriterion("zhi_day in", values, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayNotIn(List<String> values) {
            addCriterion("zhi_day not in", values, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayBetween(String value1, String value2) {
            addCriterion("zhi_day between", value1, value2, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiDayNotBetween(String value1, String value2) {
            addCriterion("zhi_day not between", value1, value2, "zhiDay");
            return (Criteria) this;
        }

        public Criteria andZhiTimeIsNull() {
            addCriterion("zhi_time is null");
            return (Criteria) this;
        }

        public Criteria andZhiTimeIsNotNull() {
            addCriterion("zhi_time is not null");
            return (Criteria) this;
        }

        public Criteria andZhiTimeEqualTo(String value) {
            addCriterion("zhi_time =", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeNotEqualTo(String value) {
            addCriterion("zhi_time <>", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeGreaterThan(String value) {
            addCriterion("zhi_time >", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeGreaterThanOrEqualTo(String value) {
            addCriterion("zhi_time >=", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeLessThan(String value) {
            addCriterion("zhi_time <", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeLessThanOrEqualTo(String value) {
            addCriterion("zhi_time <=", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeLike(String value) {
            addCriterion("zhi_time like", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeNotLike(String value) {
            addCriterion("zhi_time not like", value, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeIn(List<String> values) {
            addCriterion("zhi_time in", values, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeNotIn(List<String> values) {
            addCriterion("zhi_time not in", values, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeBetween(String value1, String value2) {
            addCriterion("zhi_time between", value1, value2, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andZhiTimeNotBetween(String value1, String value2) {
            addCriterion("zhi_time not between", value1, value2, "zhiTime");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bazitiyao
     *
     * @mbg.generated do_not_delete_during_merge Sun Aug 11 13:35:05 CST 2024
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bazitiyao
     *
     * @mbg.generated Sun Aug 11 13:35:05 CST 2024
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}