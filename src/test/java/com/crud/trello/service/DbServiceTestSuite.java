package com.crud.trello.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.service.DbServise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DbServiceTestSuite {
    @Autowired
    private DbServise dbService;

    Task task1 = new Task(11L, "title1", "content1");
    Task task2 = new Task(22L, "title2", "content2");

    @Test
    public void testGetAllTask() {
        //Given
        dbService.saveTask(task1);
        dbService.saveTask(task2);

        //When
        List<Task> getListTask = dbService.getAllTasks();

        //Then
        assertEquals(2, getListTask.size());

        //CleanUp
        for(Task task : getListTask) {
            dbService.deleteTask(task.getId());
        }

    }

    @Test
    public void testGetTaskById() {
        //Given
        Task task = dbService.saveTask(task1);

        //When
        Optional<Task> testTask = dbService.getTaskById(task.getId());

        //Then
        assertTrue(testTask.isPresent());
        assertEquals("title1", testTask.get().getTitle());
        assertEquals("content1", testTask.get().getContent());

        //CleanUp
        dbService.deleteTask(task.getId());
    }

}
