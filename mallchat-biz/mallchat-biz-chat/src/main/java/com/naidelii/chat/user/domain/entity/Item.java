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
@TableName("item")
public class Item extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 物品名称
     */
    private Integer itemName;

    /**
     * 物品类型 1道具 2徽章
     */
    private Integer itemType;
    /**
     * 物品功能描述
     */
    private String description;
    /**
     * 物品图片
     */
    private String img;

}
