package com.coder.rental.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
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
@TableName("busi_rental_type")
@ApiModel(value = "RentalType对象", description = "")
public class RentalType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("出租类型id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("出租类型名称")
    private String typeName;

    @ApiModelProperty("享受折扣")
    private Double typeDiscount;

    @TableField(exist = false)
    private Double lowDiscount;

    @TableField(exist = false)
    private Double highDiscount;

    @ApiModelProperty("备注")
    private String remark;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;
}
