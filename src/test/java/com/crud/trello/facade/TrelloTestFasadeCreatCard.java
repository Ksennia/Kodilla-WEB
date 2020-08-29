package com.crud.trello.facade;

import com.crud.tasks.trello.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.domain.TrelloCard;
import com.crud.tasks.trello.domain.cardTrello.TrelloBadgesDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.mapper.TrelloMapper;
import com.crud.trello.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloCardDto;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloTestFasadeCreatCard {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Test
    public void testCreateCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("111", "card_dto",
                "top", "list_id");
        TrelloCard trelloCard = new TrelloCard("111", "card",
                "top", "list_id");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(
                new CreatedTrelloCardDto("card_id", "card_name", "url",
                        new TrelloBadgesDto()));

        doCallRealMethod().when(trelloValidator).validateCard(trelloCard);

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloFacade.createCard(trelloCardDto);

        //Then
        verify(trelloService).createTrelloCard(trelloCardDto);
        assertEquals("card_id", createdTrelloCardDto.getId());
        assertEquals("card_name", createdTrelloCardDto.getName());
        assertEquals("url", createdTrelloCardDto.getShortUrl());
        assertNotNull(createdTrelloCardDto.getBadges());

    }

}
