package com.example.later_spring.item;

import com.example.later_spring.item.dto.AddItemRequest;
import com.example.later_spring.item.dto.ItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(ItemController.class)
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ItemControllerTest {

    @SuppressWarnings("deprecation")
    @MockBean
    ItemService itemService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemController itemController;

    AddItemRequest addItemRequest;
    ItemDto itemDto;

    @BeforeEach
    void setUp() {
        addItemRequest = new AddItemRequest("SSSS");
        itemDto = new ItemDto(1L, 1L, "assasa", Set.of());
    }

    @Test
    void addItemTest() throws Exception {
        when(itemService.addNewItem(any(), any())).thenReturn(itemDto);

        mockMvc.perform(post("/items")
                .content()
    }
}