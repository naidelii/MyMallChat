package com.naidelii.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.naidelii.data.constant.DataConstants;
import com.naidelii.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yong
 * @date 2020/2/1
 * @description 公共字段自动填充
 */
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 获取创建时间字段
        Object createTime = getFieldValByName(DataConstants.CREATE_TIME, metaObject);
        if (createTime == null) {
            setFieldValByName(DataConstants.CREATE_TIME, new Date(), metaObject);
        }
        // 获取创建人字段
        Object createBy = getFieldValByName(DataConstants.CREATE_BY, metaObject);
        if (createBy == null) {
            // 获取当前登录的用户名
            setFieldValByName(DataConstants.CREATE_BY, SecurityUtils.getUserName(), metaObject);
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 获取更新时间字段
        Object fieldValue = getFieldValByName(DataConstants.UPDATE_TIME, metaObject);
        if (fieldValue == null) {
            setFieldValByName(DataConstants.UPDATE_TIME, new Date(), metaObject);
        }
        // 获取更新人字段
        Object updateBy = getFieldValByName(DataConstants.UPDATE_BY, metaObject);
        if (updateBy == null) {
            // 获取当前登录的用户名
            setFieldValByName(DataConstants.UPDATE_BY, SecurityUtils.getUserName(), metaObject);
        }
    }

}
