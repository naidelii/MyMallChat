package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 好友申请表
 *
 * @author naidelii
 * @date 2023-12-13 22:28:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_apply")
public class UserApply extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 申请人的id
     */
    private String applicantId;
    /**
     * 接收人的id
     */
    private String receiverId;
    /**
     * 申请类型（1加好友，2加群聊）
     */
    private Integer type;
    /**
     * 状态（ 1待审批 2同意 3拒绝）
     */
    private Integer status;
    /**
     * 申请信息
     */
    private String applicationInformation;
    /**
     * 阅读状态（ 1未读 2已读）
     */
    private Integer readStatus;

}
