package com.example.later_spring.note;


import com.example.later_spring.note.dto.ItemNoteDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
interface ItemNoteService {

    @Transactional
    ItemNoteDto addNewItemNote(long userId, ItemNoteDto itemNoteDto);

    List<ItemNoteDto> searchNotesByUrl(String url, Long userId);

    List<ItemNoteDto> searchNotesByTag(long userId, String tag);

    List<ItemNoteDto> listAllItemsWithNotes(long userId, int from, int size);
}
  