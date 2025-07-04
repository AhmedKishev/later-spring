package com.example.later_spring.note;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemNoteRepository extends JpaRepository<ItemNote, Long> {
    List<ItemNote> findAllByItemUrlContainingAndItemUserId(String itemUrl, long userId);

    @Query("select itNote " +
            "from ItemNote as itNote " +
            "join itNote.item as i " +
            "where i.user.id = ?1" +
            "  and ?2 member of i.tags")
    List<ItemNote> findByTag(Long userId, String tag);

    Page<ItemNote> findAllByItemUserId(long userId, Pageable page);
}
