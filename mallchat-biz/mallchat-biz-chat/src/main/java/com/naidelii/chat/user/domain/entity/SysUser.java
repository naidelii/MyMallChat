package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author naidelii
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别 1男，2女
     */
    private Integer sex;

    /**
     * 微信用户标识
     */
    private String openId;

    /**
     * 上下线状态 1在线 2离线
     */
    private Integer activeStatus;

    /**
     * 最后上下线时间
     */
    private Date lastOptTime;

    /**
     * ip信息
     */
    private String ipInfo;

    /**
     * 用户状态(1-正常,2-冻结)
     */
    private Integer status;

    /**
     * 佩戴的徽章id
     */
    private String itemId;

}
