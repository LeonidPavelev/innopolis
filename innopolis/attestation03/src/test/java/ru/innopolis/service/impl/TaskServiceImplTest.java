package ru.innopolis.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.innopolis.entity.Task;
import ru.innopolis.entity.User;
import ru.innopolis.exception.ResourceNotFoundException;
import ru.innopolis.repository.TaskRepository;
import ru.innopolis.service.UserService;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task testTask;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setUser(testUser);
    }

    @Test
    void getAllTasksForUserShouldReturnTasksList() {
        List<Task> expectedTasks = Arrays.asList(testTask);
        when(taskRepository.findByUserIdAndDeletedFalse(anyLong())).thenReturn(expectedTasks);

        List<Task> result = taskService.getAllTasksForUser(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findByUserIdAndDeletedFalse(1L);
    }

    @Test
    void getAllTasksForUserShouldReturnEmptyList() {
        when(taskRepository.findByUserIdAndDeletedFalse(anyLong())).thenReturn(List.of());

        List<Task> result = taskService.getAllTasksForUser(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getTaskByIdShouldReturnTask() {
        when(taskRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.of(testTask));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void getTaskByIdShouldThrowExceptionWhenNotFound() {
        when(taskRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void createTaskShouldSaveTask() {
        when(userService.getUserById(anyLong())).thenReturn(testUser);
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.createTask(testTask, 1L);

        assertNotNull(result);
        assertEquals(testUser, result.getUser());
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void updateTaskShouldUpdateTaskFields() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("New Description");
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);
        updatedTask.setPriority(Priority.MEDIUM);

        when(taskRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        assertEquals(Priority.MEDIUM, result.getPriority());
    }

    @Test
    void deleteTaskShouldCallSoftDelete() {
        doNothing().when(taskRepository).softDelete(anyLong());

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).softDelete(1L);
    }
}