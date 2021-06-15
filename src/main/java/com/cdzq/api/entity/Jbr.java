package com.cdzq.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "com.cdzq.api.entity.Jbr",description = "经办人信息")
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

    @NotNull(message = "营业部编号为空")
    @ApiModelProperty(value = "营业部编号")
    @Column(name = "brach_no")
    private Integer brachNo;

    @NotNull(message = "经办人姓名为空")
    @ApiModelProperty(value = "经办人姓名")
    @Column(name = "jbr_name")
    private String jbrName;

    @ApiModelProperty(value = "经办人证书")
    @Column(name = "jbr_zs")
    private String jbrZs;

    @ApiModelProperty(value = "排序号")
    @Column(name = "sortno")
    private Integer sortno;

}