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
@TableName("auto_info")
@ApiModel(value = "AutoInfo对象", description = "")
public class AutoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("车辆信息id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("车辆号码")
    private String autoNum;

    @ApiModelProperty("厂商id")
    private Integer makerId;

    @ApiModelProperty("品牌id")
    private Integer brandId;

    @ApiModelProperty("车辆类型 0 燃油车 1 电动车 2 混动车")
    private Integer infoType;

    @ApiModelProperty("车辆颜色")
    private String color;

    @ApiModelProperty("汽车排量")
    private Double displacement;

    @ApiModelProperty("排量计量单位")
    private String unit;

    @ApiModelProperty("行驶里程")
    private Integer mileage;

    @ApiModelProperty("日租金额")
    private Integer rent;

    @ApiModelProperty("上牌日期")
    private LocalDate registrationDate;

    @ApiModelProperty("车辆图片")
    private String pic;

    @ApiModelProperty("押金")
    private Integer deposit;

    @ApiModelProperty("状态 0未租 1已租 2维保 3自用")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("应保次数")
    private Integer expectedNum;

    @ApiModelProperty("实保次数")
    private Integer actualNum;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @TableField(exist = false)
    private String makerName;

    @TableField(exist = false)
    private String brandName;

    @TableField(exist = false)
    private Double lowRent;

    @TableField(exist = false)
    private Double highRent;

    @TableField(exist = false)
    private LocalDate lowRegistrationDate;

    @TableField(exist = false)
    private LocalDate highRegistrationDate;
}
