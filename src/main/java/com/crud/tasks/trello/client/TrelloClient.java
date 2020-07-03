package com.crud.tasks.trello.client;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.TrelloBoardDto;
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

    private URI getUrl() {
        return   UriComponentsBuilder.fromHttpUrl(trelloApiEndPoint + "/members/" + trelloMember + "/boards")
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .build().encode().toUri();

    }
    public List<TrelloBoardDto> getTrelloBoards() throws TaskNotFoundException {

        TrelloBoardDto[] boardResponse = restTemplate.getForObject(getUrl().toString(),
                TrelloBoardDto[].class);

        return  Optional.of(Arrays.stream(boardResponse)
                .filter(p -> p.getName().contains("Kodilla"))
                .collect(Collectors.toList()))
                .orElseThrow(TaskNotFoundException::new);


    }
}
