package com.crud.trello.facade;

import com.crud.trello.domain.*;
import com.crud.trello.mapper.TrelloMapper;
import com.crud.trello.validator.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class TrelloFacadeTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "test", "test");
        //When
        TrelloCard trelloCardTest = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(new TrelloCard("test", "test", "test", "test"), trelloCardTest);
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test", "test", "test");
        //When
        TrelloCardDto trelloCardTest = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(new TrelloCardDto("test", "test", "test", "test"), trelloCardTest);

    }

    @Test
    void testMapToBoard() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        TrelloListDto listDto = new TrelloListDto("test", "test", true);
        trelloListDtos.add(listDto);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test", "test", trelloListDtos);
        trelloBoardDtos.add(trelloBoardDto);
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(1, trelloBoards.size());
    }

    @Test
    void testMapToBoardDto() {
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloList list = new TrelloList("test", "test", true);
        trelloList.add(list);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("test", "test", trelloList);
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(1, trelloBoards.size());
    }

    @Test
    void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        TrelloListDto listDto = new TrelloListDto("test", "test", true);
        trelloListDtos.add(listDto);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals(1, trelloLists.size());
    }

    @Test
    void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        TrelloList list = new TrelloList("test", "test", true);
        trelloLists.add(list);
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(1, trelloListDtos.size());
    }
}
