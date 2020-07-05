package com.crud.tasks.trello.client;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.mapper.CreatedTrelloCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndPoint;

    @Value("${trello.app.key}")
    private String trelloApiKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.membername}")
    private String trelloMember;

    @Autowired
    private RestTemplate restTemplate;

    //GET/1/members/{id}/boards
    private URI getUrl() {
        return   UriComponentsBuilder.fromHttpUrl(trelloApiEndPoint + "/members/" + trelloMember + "/boards")
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .build().encode().toUri();

    }

    //GET request
    public List<TrelloBoardDto> getTrelloBoards() throws TaskNotFoundException {

        TrelloBoardDto[] boardResponse = restTemplate.getForObject(getUrl().toString(),
                TrelloBoardDto[].class);

        return  Optional.of(Arrays.stream(boardResponse)
                .collect(Collectors.toList()))
                .orElseThrow(TaskNotFoundException::new);


    }

    //POST request
    public CreatedTrelloCard createdCard (TrelloCardDto trelloCardDto) {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndPoint + "/cards")
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}
