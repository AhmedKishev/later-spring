package com.example.later_spring.note;

import com.example.later_spring.note.dto.ItemNoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class ItemNoteController {

    private final ItemNoteService itemNoteService;

    @GetMapping(params = "url")
    public List<ItemNoteDto> searchByUrl(@RequestHeader("X-Later-User-Id") long userId,
                                         @RequestParam(name = "url") String url) {
        return itemNoteService.searchNotesByUrl(url, userId); // возвращает список пользовательских заметок к ссылкам, соответствующим переданному URL-адресу или его части
    }

    @GetMapping(params = "tag")
    public List<ItemNoteDto> searchByTags(@RequestHeader("X-Later-User-Id") long userId,
                                          @RequestParam(name = "tag") String tag) {
        return itemNoteService.searchNotesByTag(userId, tag);// возвращает список заметок пользователя к ссылкам с указанным тегом
    }

    @GetMapping
    public List<ItemNoteDto> listAllNotes(@RequestHeader("X-Later-User-Id") long userId,
                                          @RequestParam(name = "from", defaultValue = "0") int from,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return itemNoteService.listAllItemsWithNotes(userId, from, size);// возвращает набор пользовательских заметок, соответствующий указанным параметрам пагинации
    }

    @PostMapping
    public ItemNoteDto add(@RequestHeader("X-Later-User-Id") Long userId, @RequestBody ItemNoteDto itemNote) {
        return itemNoteService.addNewItemNote(userId, itemNote);// добавляет новую заметку к сохранённой ссылке
    }
}

