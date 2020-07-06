package com.crud.tasks.domain.cardTrello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBadgesDto {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("attachmentsByType")
    private TrelloAttachmentByTypeDto attachments;
}
