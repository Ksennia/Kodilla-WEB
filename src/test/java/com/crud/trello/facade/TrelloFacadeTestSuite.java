package com.crud.trello.facade;

import com.crud.tasks.trello.domain.*;
import com.crud.tasks.trello.mapper.TrelloMapper;
import com.crud.tasks.trello.validator.TrelloCardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloFacadeTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "testing_card_dto", "top", "1111");
        //When
        TrelloCard trelloCardTest = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("test_name", trelloCardTest.getName());
        assertEquals("testing_card_dto", trelloCardTest.getDescription());
        assertEquals("1111", trelloCardTest.getListId());
        assertEquals("top", trelloCardTest.getPos());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test_name", "testing_card", "bottom", "1111");
        //When
        TrelloCardDto trelloCardDtoTest = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("test_name", trelloCardDtoTest.getName());
        assertEquals("testing_card", trelloCardDtoTest.getDescription());
        assertEquals("1111", trelloCardDtoTest.getListId());
        assertEquals("bottom", trelloCardDtoTest.getPos());

    }

    @Test
    public void testMapToBoard() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<TrelloListDto>();
        TrelloListDto listDto = new TrelloListDto("1111", "test_list_dto", true);
        trelloListDtos.add(listDto);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<TrelloBoardDto>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("2222", "test_name_board_dto", trelloListDtos);
        trelloBoardDtos.add(trelloBoardDto);
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(1, trelloBoards.size());
    }

    @Test
    public void testMapToBoardDto() {
        List<TrelloList> trelloList = new ArrayList<TrelloList>();
        TrelloList list = new TrelloList("1111", "test_list_dto", true);
        trelloList.add(list);
        List<TrelloBoard> trelloBoards = new ArrayList<TrelloBoard>();
        TrelloBoard trelloBoard = new TrelloBoard("2222", "test_board", trelloList);
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(1, trelloBoards.size());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<TrelloListDto>();
        TrelloListDto listDto = new TrelloListDto("1111", "test_list_dto", true);
        trelloListDtos.add(listDto);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals(1, trelloLists.size());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<TrelloList>();
        TrelloList list = new TrelloList("1111", "test_list", true);
        trelloLists.add(list);
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(1, trelloListDtos.size());
    }
}
