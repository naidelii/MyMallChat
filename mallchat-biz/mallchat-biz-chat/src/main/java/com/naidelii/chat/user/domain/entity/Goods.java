package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物品表
 *
 * @author naidelii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("goods")
public class Goods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 物品类型 1改名卡 2徽章
     */
    private Integer type;
    /**
     * 物品功能描述
     */
    private String description;
    /**
     * 物品图片
     */
    private String img;

}
