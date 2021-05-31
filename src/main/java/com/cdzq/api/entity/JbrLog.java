package com.cdzq.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ApiModel(value = "com.cdzq.api.entity.JbrLog",description = "经办人查询日志")
@Table(name = "jbr_log")
@Getter
@Setter
public class JbrLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @Column(name = "systime")
    private Date systime;

    @Column(name = "sysip")
    private String sysip;

    @NotNull(message = "营业部编号为空")
    @ApiModelProperty(value = "营业部ID")
    @Column(name = "brach_no" )
    private Integer brachNo;

    @NotNull(message = "资金账号为空")
    @ApiModelProperty(value = "资金账号")
    @Column(name = "fund_account")
    private String fundAccount;

    @NotNull(message = "客户端ip为空")
    @ApiModelProperty(value = "客户端ip")
    @Column(name = "client_ip")
    private String clientIp;

    @NotNull(message = "客户端mac为空")
    @ApiModelProperty(value = "客户端mac")
    @Column(name = "client_mac")
    private String clientMac;

    @NotNull(message = "客户端手机号为空")
    @ApiModelProperty(value = "客户端手机号")
    @Column(name = "client_tel")
    private String clientTel;

    @NotNull(message = "产品帐户为空")
    @ApiModelProperty(value = "产品帐户")
    @Column(name = "secum_account")
    private String secumAccount;

    @NotNull(message = "TA编号为空")
    @ApiModelProperty(value = "TA编号")
    @Column(name = "prodta_no")
    private String prodtaNo;

    @NotNull(message = "产品编号为空")
    @ApiModelProperty(value = "产品编号")
    @Column(name = "prod_code")
    private String prodCode;


}