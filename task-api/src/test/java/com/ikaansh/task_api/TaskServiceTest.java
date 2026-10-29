
package com.ikaansh.task_api;

import com.ikaansh.task_api.entity.Task;
import com.ikaansh.task_api.repository.TaskRepository;
import com.ikaansh.task_api.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getAllTasksReturnsTasksFromRepository() {
        List<Task> tasks = List.of(mock(Task.class));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(tasks, result);
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskByIdReturnsTaskWhenFound() {
        Long id = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(id);

        assertTrue(result.isPresent());
        assertSame(task, result.get());
        verify(taskRepository).findById(id);
    }

    @Test
    void getTaskByIdReturnsEmptyWhenNotFound() {
        Long id = 999L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(id);

        assertTrue(result.isEmpty());
        verify(taskRepository).findById(id);
    }

    @Test
    void createTaskSavesAndReturnsTask() {
        Task task = mock(Task.class);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertSame(task, result);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTaskSetsIdAndSavesTask() {
        Long id = 5L;
        Task task = mock(Task.class);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTask(id, task);

        verify(task).setId(id);
        verify(taskRepository).save(task);
        assertSame(task, result);
    }

    @Test
    void deleteTaskDeletesTaskById() {
        Long id = 5L;

        taskService.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }
}
