package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 黑名单
 *
 * @author naidelii
 * @date 2023-12-05 09:36:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("black")
public class Black extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型（1：用户id，2：ip）
     */
    private Integer type;
    /**
     * 目标值
     */
    private String target;

}
