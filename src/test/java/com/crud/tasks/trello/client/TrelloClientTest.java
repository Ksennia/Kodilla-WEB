package com.crud.tasks.trello.client;

import com.crud.tasks.trello.config.TrelloConfig;
import com.crud.tasks.trello.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.cardTrello.TrelloBadgesDto;
import com.crud.tasks.trello.validator.TrelloCardDto;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

class  TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(trelloConfig.getTrelloApiEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloMember()).thenReturn("user");
    }
    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/user/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri.toString(),TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(trelloBoards.length,fetchTrelloBoards.size());
        assertEquals(trelloBoards[0].getId(), fetchTrelloBoards.get(0).getId());
        assertEquals(trelloBoards[0].getName(), fetchTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchTrelloBoards.get(0).getLists());

    }

    @Test
    void shouldCreatedCard() throws URISyntaxException{
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new TrelloBadgesDto()
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createCard(trelloCardDto);

        //Then
        Assert.assertEquals(createdTrelloCard.getId(), newCard.getId());
        Assert.assertEquals(createdTrelloCard.getName(), newCard.getName());
        Assert.assertEquals(createdTrelloCard.getShortUrl(), newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException {
        //Given

        URI uri = new URI("http://test.com/members/user/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri,TrelloBoardDto.class)).thenReturn(null);
        //When
        List<TrelloBoardDto> expection = Collections.emptyList();
        //Then
        assertEquals(expection, trelloClient.getTrelloBoards());

    }
}