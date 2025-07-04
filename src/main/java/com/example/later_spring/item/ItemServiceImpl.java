package com.example.later_spring.item;

import com.example.later_spring.exception.InsufficientPermissionException;
import com.example.later_spring.exception.LaterApplicationException;
import com.example.later_spring.item.dto.AddItemRequest;
import com.example.later_spring.item.dto.GetItemRequest;
import com.example.later_spring.item.dto.ItemDto;
import com.example.later_spring.item.dto.ModifyItemRequest;
import com.example.later_spring.item.mapper.ItemMapper;
import com.example.later_spring.user.User;
import com.example.later_spring.user.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ItemServiceImpl implements ItemService {
    ItemRepository repository;
    UserRepository userRepository;
    UrlMetadataRetriever urlMetaDataRetriever;
    ItemMapper itemMapper;

    @Override
    public List<ItemDto> getItems(long userId) {
        List<Item> userItems = repository.findByUserId(userId);
        return userItems.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDto addNewItem(Long userId, AddItemRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InsufficientPermissionException("You do not have permission to perform this operation"));


        UrlMetadataRetriever.UrlMetadata result = urlMetaDataRetriever.retrieve(request.getUrl());

        Optional<Item> maybeExistingItem = repository.findByUserAndResolvedUrl(user, result.getResolvedUrl());
        Item item;
        if (maybeExistingItem.isEmpty()) {
            item = repository.save(itemMapper.toItem(result, user, request.getTags()));
        } else {
            item = maybeExistingItem.get();
            if (request.getTags() != null && !request.getTags().isEmpty()) {
                item.getTags().addAll(request.getTags());
                repository.save(item);
            }
        }
        return itemMapper.toItemDto(item);
    }

    @Override
    @Transactional
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndId(userId, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getUserItems(String lastName) {
        List<Item> foundItems = repository.findItemsByLastNamePrefix(lastName);
        return foundItems.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto changeItem(long userId, ModifyItemRequest request) {
        Optional<Item> maybeItem = getAndCheckPermissions(userId, request.getItemId());
        if (maybeItem.isPresent()) {
            Item item = maybeItem.get();
            item.setUnread(!request.isRead());
            if (request.isReplaceTags()) {
                item.getTags().clear();
            }
            if (request.hasTags()) {
                item.getTags().addAll(request.getTags());
            }
            item = repository.save(item);
            return itemMapper.toItemDto(item);
        } else {
            throw new LaterApplicationException("The item with id " + request.getItemId() + " was not found");
        }
    }

    private Optional<Item> getAndCheckPermissions(long userId, long itemId) {
        Optional<Item> maybeItem = repository.findById(itemId);
        if (maybeItem.isPresent()) {
            Item item = maybeItem.get();
            if (!item.getUser().getId().equals(userId)) {
                throw new InsufficientPermissionException("You do not have permission to perform this operation");
            }
        }
        return maybeItem;
    }


    private Sort makeOrderByClause(GetItemRequest.Sort sort) {
        return switch (sort) {
            case TITLE -> Sort.by("title").ascending();
            case SITE -> Sort.by("resolvedUrl").ascending();
            case OLDEST -> Sort.by("dateResolved").ascending();
            default -> Sort.by("dateResolved").descending();
        };
    }
}