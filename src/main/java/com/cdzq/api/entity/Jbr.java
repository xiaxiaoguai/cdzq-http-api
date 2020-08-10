package com.cdzq.api.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "jbr")
public class Jbr {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "经办人编码")
    @Column(name = "jbr_id")
    private Integer jbrId;

    @ApiModelProperty(value = "营业部ID")
    @Column(name = "brach_no")
    private Integer brachNo;

    @ApiModelProperty(value = "经办人姓名")
    @Column(name = "jbr_name")
    private String jbrName;

    @ApiModelProperty(value = "经办人证书")
    @Column(name = "jbr_zs")
    private String jbrZs;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return jbr_id
     */
    public Integer getJbrId() {
        return jbrId;
    }

    /**
     * @param jbrId
     */
    public void setJbrId(Integer jbrId) {
        this.jbrId = jbrId;
    }

    /**
     * @return brach_no
     */
    public Integer getBrachNo() {
        return brachNo;
    }

    /**
     * @param brachNo
     */
    public void setBrachNo(Integer brachNo) {
        this.brachNo = brachNo;
    }

    /**
     * @return jbr_name
     */
    public String getJbrName() {
        return jbrName;
    }

    /**
     * @param jbrName
     */
    public void setJbrName(String jbrName) {
        this.jbrName = jbrName;
    }

    /**
     * @return jbr_zs
     */
    public String getJbrZs() {
        return jbrZs;
    }

    /**
     * @param jbrZs
     */
    public void setJbrZs(String jbrZs) {
        this.jbrZs = jbrZs;
    }
}