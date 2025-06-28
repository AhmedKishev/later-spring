package com.example.later_spring.note;


import com.example.later_spring.item.Item;
import com.example.later_spring.item.ItemRepository;
import com.example.later_spring.note.dto.ItemNoteDto;
import com.example.later_spring.note.mapper.ItemNoteMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemNoteServiceImpl implements ItemNoteService {

    ItemNoteRepository itemNoteRepository;

    ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemNoteDto addNewItemNote(long userId, ItemNoteDto itemNoteDto) {
        Item item = itemRepository.findById(itemNoteDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        ItemNote itemNote = itemNoteRepository.save(ItemNoteMapper.mapToItemNote(itemNoteDto, item));
        itemNoteRepository.save(itemNote);
        return ItemNoteMapper.mapToItemNoteDto(itemNote);
    }

    @Override
    @Transactional
    public List<ItemNoteDto> searchNotesByUrl(String url, Long userId) {
        List<ItemNote> itemNotes = itemNoteRepository.findAllByItemUrlContainingAndItemUserId(url, userId);
        return ItemNoteMapper.mapToItemNoteDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> searchNotesByTag(long userId, String tag) {
        List<ItemNote> itemNotes = itemNoteRepository.findByTag(userId, tag);
        return ItemNoteMapper.mapToItemNoteDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> listAllItemsWithNotes(long userId, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return itemNoteRepository.findAllByItemUserId(userId, page)
                .map(ItemNoteMapper::mapToItemNoteDto)
                .getContent();
    }
}
