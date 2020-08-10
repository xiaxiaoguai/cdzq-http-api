package com.cdzq.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@ApiModel(value = "com.cdzq.api.entity.JbrLog",description = "经办人查询日志")
@Table(name = "jbr_log")
@ToString
public class JbrLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "systime")
    private Date systime;

    @ApiModelProperty(value = "营业部ID")
    @Column(name = "brach_no")
    private Integer brachNo;

    @ApiModelProperty(value = "资金账号")
    @Column(name = "fund_account")
    private String fundAccount;

    @ApiModelProperty(value = "客户端ip")
    @Column(name = "client_ip")
    private String clientIp;

    @ApiModelProperty(value = "客户端mac")
    @Column(name = "client_mac")
    private String clientMac;

    @ApiModelProperty(value = "客户端手机号")
    @Column(name = "client_tel")
    private String clientTel;

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
     * @return systime
     */
    public Date getSystime() {
        return systime;
    }

    /**
     * @param systime
     */
    public void setSystime(Date systime) {
        this.systime = systime;
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
     * @return fund_account
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * @param fundAccount
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * @return client_ip
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * @param clientIp
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return client_mac
     */
    public String getClientMac() {
        return clientMac;
    }

    /**
     * @param clientMac
     */
    public void setClientMac(String clientMac) {
        this.clientMac = clientMac;
    }

    /**
     * @return client_tel
     */
    public String getClientTel() {
        return clientTel;
    }

    /**
     * @param clientTel
     */
    public void setClientTel(String clientTel) {
        this.clientTel = clientTel;
    }
}