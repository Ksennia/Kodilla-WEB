package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor

public class TaskDto {
    private  Long id;
    private String title;
    private String content;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrelloCardDto {

        private String name;
        private String description;
        private String pos;
        private String listId;
    }
}
