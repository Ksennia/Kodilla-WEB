package com.crud.tasks.trello.client;

import org.junit.jupiter.api.Test;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        //when(trelloConfig.getTrelloMember()).thenReturn("user");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/kseniyabinkovskaya/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(1,fetchTrelloBoards.size());
        Assert.assertEquals("test_id", fetchTrelloBoards.get(0).getId());
        Assert.assertEquals("test_board", fetchTrelloBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchTrelloBoards.get(0).getLists());

    }

}