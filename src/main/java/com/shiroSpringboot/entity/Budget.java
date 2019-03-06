/*
 * @ClassName Budget
 * @Description 
 * @version 1.0
 * @Date 2019-01-18 14:05:15
 */
package com.shiroSpringboot.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Budget {
    /**
     * @Fields id 
     */
    private Long id;
    /**
     * @Fields budgetName 预算名称
     */
    private String budgetName;
    /**
     * @Fields budgetCode 预算编号
     */
    private String budgetCode;
    /**
     * @Fields createTime 创建时间
     */
    private Date createTime;
    /**
     * @Fields createId 创建人id
     */
    private Integer createId;
    /**
     * @Fields budgetAmount 预算总和
     */
    private BigDecimal budgetAmount;
    /**
     * @Fields projectId 项目表id
     */
    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName == null ? null : budgetName.trim();
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode == null ? null : budgetCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}