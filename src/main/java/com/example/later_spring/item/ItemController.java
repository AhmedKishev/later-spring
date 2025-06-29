package com.example.later_spring.item;


import com.example.later_spring.item.dto.AddItemRequest;
import com.example.later_spring.item.dto.GetItemRequest;
import com.example.later_spring.item.dto.ItemDto;
import com.example.later_spring.item.dto.ModifyItemRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {
    ItemService itemService;

    @GetMapping(params = "lastName")
    public List<ItemDto> get(@RequestParam(name = "lastName") String lastName) {
        return itemService.getUserItems(lastName);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") Long userId,
                       @RequestBody AddItemRequest request) {
        return itemService.addNewItem(userId, request);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable(name = "itemId") long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @PatchMapping
    public ItemDto modifyItem(@RequestHeader("X-Later-User-Id") long userId,
                              @RequestBody ModifyItemRequest request) {
        return itemService.changeItem(userId, request);
    }
}