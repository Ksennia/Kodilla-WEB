package com.crud.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloListDto;
import com.crud.tasks.trello.domain.cardTrello.TrelloBadgesDto;
import com.crud.tasks.trello.validator.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTestSuite {
    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;


    @Test
    public void testFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("list_id", "List_name", true));
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("board_id", "test_board", trelloListDto);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When
        List<TrelloBoardDto> checkedTrelloBoards = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(trelloBoardDto.getId(), checkedTrelloBoards.get(0).getId());
        assertEquals(trelloBoardDto.getName(), checkedTrelloBoards.get(0).getName());
        checkedTrelloBoards.get(0).getLists().forEach(
                trelloListTest -> {
                    assertEquals("list_id", trelloListTest.getId());
                    assertEquals("List_name", trelloListTest.getName());
                    assertTrue(trelloListTest.isClosed());
                }
        );
    }

    @Test
    public void testCreateTrelloCard() {
        //Given

        TrelloCardDto trelloCardDto = new TrelloCardDto("card_id", "card_name", "top", "list_id");

        doCallRealMethod().when(adminConfig).getAdminMail();
        when(trelloClient.createCard(trelloCardDto)).thenReturn(new CreatedTrelloCardDto("card_id", "card_name", "url",
                new TrelloBadgesDto()));

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        verify(emailService).send(any());
        verify(trelloClient).createCard(trelloCardDto);
        assertEquals("card_id", createdTrelloCardDto.getId());
        assertEquals("card_name", createdTrelloCardDto.getName());
        assertEquals("url", createdTrelloCardDto.getShortUrl());
        assertNotNull(createdTrelloCardDto.getBadges());

    }

}
