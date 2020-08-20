package com.crud.tasks.trello.facade;

import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloCard;
import com.crud.tasks.trello.validator.TrelloCardDto;
import com.crud.tasks.trello.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.domain.TrelloBoard;
import com.crud.tasks.trello.mapper.TrelloMapper;
import com.crud.tasks.trello.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {
    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    @Autowired
    private TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoads() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBOards(trelloBoards);
        return trelloMapper.mapToBoardsDto(filteredBoards);
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
