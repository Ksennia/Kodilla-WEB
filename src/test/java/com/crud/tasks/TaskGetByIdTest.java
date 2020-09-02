package com.crud.tasks;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbServise;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskGetByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DbServise service;

//    @MockBean
//    private TaskMapper mapper;

    @Test
    void shouldFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test TitleDTO", "Test ContentDTO");

        Task task = new Task(1L, "Test Title", "Test Content");
        service.saveTask(task);

     //   when(mapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When$Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test TitleDTO")))
                .andExpect(jsonPath("$.content", is("Test ContentDTO")));

    }
}
