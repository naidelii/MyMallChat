package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.Item;
import com.naidelii.chat.user.mapper.ItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author naidelii
 */
@Repository
public class ItemDao extends ServiceImpl<ItemMapper, Item> {

    public List<Item> getByType(Integer type) {
        return lambdaQuery()
                .eq(Item::getItemType, type)
                .list();
    }
}
