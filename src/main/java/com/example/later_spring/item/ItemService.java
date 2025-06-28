package com.example.later_spring.item;

import com.example.later_spring.item.dto.AddItemRequest;
import com.example.later_spring.item.dto.GetItemRequest;
import com.example.later_spring.item.dto.ItemDto;
import com.example.later_spring.item.dto.ModifyItemRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    List<ItemDto> getItems(long userId);

    @Transactional
    ItemDto addNewItem(Long userId, AddItemRequest request);

    @Transactional
    void deleteItem(long userId, long itemId);


    List<ItemDto> getItems(GetItemRequest req);

    ItemDto changeItem(long userId, ModifyItemRequest request);

    @Transactional(readOnly = true)
    List<ItemDto> getUserItems(String lastName);
}