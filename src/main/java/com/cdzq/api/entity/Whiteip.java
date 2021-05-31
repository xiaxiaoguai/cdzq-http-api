package com.cdzq.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "com.cdzq.api.entity.Whiteip",description = "白名单IP列表")
@Table(name = "whiteip")
public class Whiteip {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @NotNull(message = "IP为空")
    @ApiModelProperty(value = "IP")
    @Column(name = "ip")
    private String ip;

    @ApiModelProperty(value = "业务类型-此处传->jbr")
    @Column(name = "ywlx")
    private String ywlx;
}