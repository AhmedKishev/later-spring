package com.example.later_spring.item.mapper;


import com.example.later_spring.item.Item;
import com.example.later_spring.item.UrlMetadataRetriever;
import com.example.later_spring.item.dto.ItemDto;
import com.example.later_spring.user.User;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toItem(UrlMetadataRetriever.UrlMetadata urlMetadata, User user, Set<String> tags);

    ItemDto toItemDto(Item item);
}
