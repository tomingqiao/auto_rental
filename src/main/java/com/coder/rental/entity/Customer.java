package com.coder.rental.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("busi_customer")
@ApiModel(value = "Customer对象", description = "")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("客户姓名")
    private String name;

    @ApiModelProperty("客户年龄")
    private Integer age;

    @ApiModelProperty("手机号码")
    private String tel;

    @ApiModelProperty("出生日期")
    private LocalDate birthday;

    @ApiModelProperty("身份证号码")
    private String idNum;

    @ApiModelProperty("客户状态 0黑名单 1白名单")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @ApiModelProperty("性别 0女 1男")
    private Integer gender;

    @TableField(exist = false)
    private Integer lowAge;

    @TableField(exist = false)
    private Integer highAge;
}
