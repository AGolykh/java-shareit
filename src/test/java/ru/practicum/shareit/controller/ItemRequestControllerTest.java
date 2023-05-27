package ru.practicum.shareit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestInputDto;
import ru.practicum.shareit.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ItemRequestController.class)
public class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemRequestService itemRequestServiceMock;

    @SneakyThrows
    @Test
    void getByRequesterId_return2ItemRequest_add2ItemRequest() {
        ItemRequestDto itemRequestDto1 = new ItemRequestDto();
        itemRequestDto1.setId(1L);
        itemRequestDto1.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto1.setDescription("wqewqewq");
        itemRequestDto1.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        ItemRequestDto itemRequestDto2 = new ItemRequestDto();
        itemRequestDto2.setId(2L);
        itemRequestDto2.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto2.setDescription("wqewqwewrewewq");
        itemRequestDto2.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        when(itemRequestServiceMock.getByRequesterId(1L))
                .thenReturn(List.of(itemRequestDto1, itemRequestDto2));

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$[0].created").value(itemRequestDto1.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$[0].requester.id").value(itemRequestDto1.getRequester().getId()))
                .andExpect(jsonPath("$[0].requester.name").value(itemRequestDto1.getRequester().getName()))
                .andExpect(jsonPath("$[1].id").value(itemRequestDto2.getId()))
                .andExpect(jsonPath("$[1].description").value(itemRequestDto2.getDescription()))
                .andExpect(jsonPath("$[1].created").value(itemRequestDto2.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$[1].requester.id").value(itemRequestDto2.getRequester().getId()))
                .andExpect(jsonPath("$[1].requester.name").value(itemRequestDto2.getRequester().getName()));
        verify(itemRequestServiceMock).getByRequesterId(1L);
    }


    @SneakyThrows
    @Test
    void getAll_return2ItemRequest_add2ItemRequest() {
        ItemRequestDto itemRequestDto1 = new ItemRequestDto();
        itemRequestDto1.setId(1L);
        itemRequestDto1.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto1.setDescription("wqewqewq");
        itemRequestDto1.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        ItemRequestDto itemRequestDto2 = new ItemRequestDto();
        itemRequestDto2.setId(2L);
        itemRequestDto2.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto2.setDescription("wqewqwewrewewq");
        itemRequestDto2.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        when(itemRequestServiceMock.getAll(1L, 1, 20))
                .thenReturn(List.of(itemRequestDto1, itemRequestDto2));

        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1L)
                        .param("from", "1")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$[0].created").value(itemRequestDto1.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$[0].requester.id").value(itemRequestDto1.getRequester().getId()))
                .andExpect(jsonPath("$[0].requester.name").value(itemRequestDto1.getRequester().getName()))
                .andExpect(jsonPath("$[1].id").value(itemRequestDto2.getId()))
                .andExpect(jsonPath("$[1].description").value(itemRequestDto2.getDescription()))
                .andExpect(jsonPath("$[1].created").value(itemRequestDto2.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$[1].requester.id").value(itemRequestDto2.getRequester().getId()))
                .andExpect(jsonPath("$[1].requester.name").value(itemRequestDto2.getRequester().getName()));
        verify(itemRequestServiceMock).getAll(1L, 1, 20);
    }

    @SneakyThrows
    @Test
    void getById_returnItemRequest_addItemRequest() {
        ItemRequestDto itemRequestDto1 = new ItemRequestDto();
        itemRequestDto1.setId(1L);
        itemRequestDto1.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto1.setDescription("wqewqewq");
        itemRequestDto1.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        when(itemRequestServiceMock.getById(1L, 1L))
                .thenReturn(itemRequestDto1);

        mockMvc.perform(get("/requests/{requestId}", itemRequestDto1.getId())
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$.created").value(itemRequestDto1.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.requester.id").value(itemRequestDto1.getRequester().getId()))
                .andExpect(jsonPath("$.requester.name").value(itemRequestDto1.getRequester().getName()));
        verify(itemRequestServiceMock).getById(1L, 1L);
    }

    @SneakyThrows
    @Test
    void create_returnItemRequest_addItemRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setRequester(new UserShortDto(1L, "wqewqewq"));
        itemRequestDto.setDescription("wqewqewq");
        itemRequestDto.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        ItemRequestInputDto itemRequestInputDto = new ItemRequestInputDto(itemRequestDto.getDescription());

        when(itemRequestServiceMock.create(1L, itemRequestInputDto))
                .thenReturn(itemRequestDto);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .content(objectMapper.writeValueAsString(itemRequestInputDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()))
                .andExpect(jsonPath("$.created").value(itemRequestDto.getCreated()
                        .truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.requester.id").value(itemRequestDto.getRequester().getId()))
                .andExpect(jsonPath("$.requester.name").value(itemRequestDto.getRequester().getName()));
        verify(itemRequestServiceMock).create(1L, itemRequestInputDto);
    }
}
