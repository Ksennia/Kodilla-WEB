package com.crud.tasks.trello.client;



import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.mapper.CreatedTrelloCard;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;


    @Autowired
    private RestTemplate restTemplate;

    //GET/1/members/{id}/boards

    private URI getUrl() {
        return   UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndPoint() + "/members/" + trelloConfig.getTrelloMember() + "/boards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .build().encode().toUri();

    }

    //GET request
    public List<TrelloBoardDto> getTrelloBoards(){

        try {
        TrelloBoardDto[] boardResponse = restTemplate.getForObject(getUrl().toString(),
                TrelloBoardDto[].class);
        return Arrays.asList(ofNullable(boardResponse).orElse(new TrelloBoardDto[0]));}
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }

//        return  Optional.of(Arrays.stream(boardResponse)
//                .collect(Collectors.toList()))
//                .orElse(TaskNotFoundException::new);


    }

    //POST request
    public CreatedTrelloCard createCard (TrelloCardDto trelloCardDto) {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndPoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}
