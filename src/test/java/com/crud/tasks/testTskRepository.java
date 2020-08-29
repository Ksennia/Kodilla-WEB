package com.crud.tasks;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class testTskRepository {
    @Autowired
    private TaskRepository repository;

    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(1l, "taskName", "test description");
        //When
        Task testTask = repository.save(task);
        //Then
        assertEquals("taskName", testTask.getTitle());
        assertEquals("test description", testTask.getContent());
        //CleanUp
        repository.deleteAll();
    }

    @Test
    public void testFindById() {
        //Given
        Task task = repository.save(new Task(1l, "taskName", "test description" ));
        //When
        Optional<Task> testTask = repository.findById(task.getId());
        //Then
        assertTrue(testTask.isPresent());
        assertEquals("taskName", testTask.get().getTitle());
        assertEquals("test description", testTask.get().getContent());
        //CleanUp
        repository.deleteById(task.getId());
    }

    @Test
    public void testFindAll() {
        //Given
        Task task1 = repository.save(new Task(1l, "taskName first", "test description" ));
        Task task2 = repository.save(new Task(2l, "taskName second", "test description" ));
        //When
        List<Task> tasks = repository.findAll();
        //Then
        assertEquals(2, tasks.size());
        tasks.forEach(task -> {
            assertNotNull(task.getId());
            assertNotNull(task.getTitle());
            assertNotNull(task.getContent());
        });
        //CleanUp
        repository.deleteAll();
    }

    @Test
    public void testCount() {
        //Given
        Task task1 = repository.save(new Task(1l, "taskName first", "test description" ));
        Task task2 = repository.save(new Task(2l, "taskName second", "test description" ));
        //When
        long counter = repository.count();
        //Then
        assertEquals(2l, counter);

        //CleanUp
        repository.deleteAll();
    }
}
