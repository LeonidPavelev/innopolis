package ru.innopolis.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.innopolis.dto.TaskRequestDto;
import ru.innopolis.dto.TaskResponseDto;
import ru.innopolis.entity.Task;
import ru.innopolis.security.UserPrincipal;
import ru.innopolis.service.impl.TaskServiceImpl;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskApiControllerTest {

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskApiController taskController;

    private Authentication authentication;


    @BeforeEach
    void setUp() {
       UserPrincipal userPrincipal = new UserPrincipal(
                1L,
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Test
    void getAllTasksShouldReturnTasksList() {
        Task task = new Task();
        task.setId(1L);
        when(taskService.getAllTasksForUser(anyLong()))
                .thenReturn(Collections.singletonList(task));

        ResponseEntity<List<TaskResponseDto>> response = taskController.getAllTasks(authentication);
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        verify(taskService).getAllTasksForUser(1L);
    }

    @Test
    void getTaskShouldReturnTask() {
        Task task = new Task();
        task.setId(1L);
        when(taskService.getTaskById(anyLong(), anyLong()))
                .thenReturn(task);

        ResponseEntity<TaskResponseDto> response = taskController.getTask(1L, authentication);
        assertNotNull(response);
        assertEquals(1L, response.getBody().getId());
        verify(taskService).getTaskById(1L, 1L);
    }

    @Test
    void createTaskShouldReturnCreatedTask() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Test Task");
        request.setStatus(TaskStatus.NEW);
        request.setPriority(Priority.MEDIUM);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Test Task");
        when(taskService.createTask(any(Task.class), anyLong()))
                .thenReturn(savedTask);

        ResponseEntity<TaskResponseDto> response = taskController.createTask(request, authentication);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Task", response.getBody().getTitle());
        verify(taskService).createTask(any(Task.class), eq(1L));
    }

    @Test
    void updateTaskShouldReturnUpdatedTask() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Updated Task");
        request.setStatus(TaskStatus.IN_PROGRESS);
        request.setPriority(Priority.HIGH);

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Task");
        when(taskService.updateTask(anyLong(), any(Task.class), anyLong()))
                .thenReturn(updatedTask);

        ResponseEntity<TaskResponseDto> response = taskController.updateTask(1L, request, authentication);
        assertNotNull(response);
        assertEquals(1L, response.getBody().getId());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskService).updateTask(eq(1L), any(Task.class), eq(1L));
    }

    @Test
    void deleteTaskShouldReturnSuccessMessage() {
        doNothing().when(taskService).deleteTask(anyLong(), anyLong());
        ResponseEntity<Map<String, String>> response = taskController.deleteTask(1L, authentication);

        assertNotNull(response);
        assertEquals("Задача с ID 1 успешно удалена", response.getBody().get("message"));
        verify(taskService).deleteTask(1L, 1L);
    }
}