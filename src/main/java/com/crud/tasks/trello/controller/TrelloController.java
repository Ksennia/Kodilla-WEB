package com.crud.tasks.trello.controller;

import com.crud.tasks.trello.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloCardDto;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoads();
    }

    @PostMapping(value = "/createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard (@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }
//    List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
//    trelloBoards.stream()
//                .filter(p -> p.getName().contains("Kodilla"))
//                .forEach(trelloBoardDto -> {
//                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
//
//                    System.out.println("This board contains lists: ");
//
//                    trelloBoardDto.getLists().forEach(trelloList -> {
//                        System.out.println(trelloList.getName() + " " + trelloList.getId()
//                        + " " + trelloList.isClosed());
//                    });
//                });
//    }
//

}