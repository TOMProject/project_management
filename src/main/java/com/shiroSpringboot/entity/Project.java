/*
 * @ClassName Project
 * @Description 
 * @version 1.0
 * @Date 2019-01-18 14:05:15
 */
package com.shiroSpringboot.entity;

import java.util.Date;

public class Project {
    /**
     * @Fields id 
     */
    private Long id;
    /**
     * @Fields projectName 项目名称
     */
    private String projectName;
    /**
     * @Fields createTime 创建时间
     */
    private Date createTime;
    /**
     * @Fields createId 创建人员id
     */
    private Integer createId;
    /**
     * @Fields projectCode 项目编号
     */
    private String projectCode;
    /**
     * @Fields parentId 父级项目id
     */
    private Long parentId;
    /**
     * @Fields isLeaf 是否还有子节点：默认是0，0表示没有，1表示有
     */
    private Byte isLeaf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long long1) {
        this.parentId = long1;
    }

    public Byte getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Byte isLeaf) {
        this.isLeaf = isLeaf;
    }
}