package com.example.later_spring.item;

import com.example.later_spring.item.dto.AddItemRequest;
import com.example.later_spring.item.dto.ItemDto;
import com.example.later_spring.item.dto.ModifyItemRequest;
import com.example.later_spring.item.mapper.ItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ItemControllerTest {

    @SuppressWarnings("deprecation")
    @MockBean
    ItemService itemService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;


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
                        .header("X-Later-User-Id", 1)
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.userId", is(itemDto.getUserId()), Long.class))
                .andExpect(jsonPath("$.url", is(itemDto.getUrl())));

    }

    @Test
    void modifyItemTest() throws Exception {
        ModifyItemRequest modifyItemRequest = ModifyItemRequest.of(1L, true, Set.of(), true);
        when(itemService.changeItem(anyLong(), any())).thenReturn(itemDto);
        mockMvc.perform(patch("/items")
                        .header("X-Later-User-Id", 1)
                        .content(objectMapper.writeValueAsString(modifyItemRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.userId", is(itemDto.getUserId()), Long.class))
                .andExpect(jsonPath("$.url", is(itemDto.getUrl())));
    }
}