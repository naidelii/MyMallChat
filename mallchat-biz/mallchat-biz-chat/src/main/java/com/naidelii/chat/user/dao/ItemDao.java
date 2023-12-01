package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.Item;
import com.naidelii.chat.user.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author naidelii
 */
@Service
public class ItemDao extends ServiceImpl<ItemMapper, Item> {

    public List<Item> getByType(Integer type) {
        return lambdaQuery()
                .eq(Item::getItemType, type)
                .list();
    }
}
