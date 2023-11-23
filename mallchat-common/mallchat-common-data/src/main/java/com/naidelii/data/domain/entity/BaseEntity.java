package com.naidelii.data.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.naidelii.data.constant.DataConstants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yong
 * Entity基类
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    @TableField(value = DataConstants.DB_CREATE_BY, fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 创建时间
     */

    @TableField(value = DataConstants.DB_CREATE_TIME, fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = DataConstants.FORMAT)
    @DateTimeFormat(pattern = DataConstants.FORMAT)
    protected Date createTime;

    /**
     * 更新者
     */
    @TableField(value = DataConstants.DB_UPDATE_BY, fill = FieldFill.UPDATE)
    protected String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = DataConstants.DB_UPDATE_TIME, fill = FieldFill.UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = DataConstants.FORMAT)
    @DateTimeFormat(pattern = DataConstants.FORMAT)
    protected Date updateTime;

}
