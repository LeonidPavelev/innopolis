package ru.innopolis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import ru.innopolis.entity.Task;
import ru.innopolis.entity.User;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setEnabled(true);
        return entityManager.persist(user);
    }

    private Task createTestTask(User user) {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.NEW);
        task.setPriority(Priority.MEDIUM);
        task.setUser(user);
        return entityManager.persist(task);
    }

    @Test
    void findByUserIdAndDeletedFalseShouldReturnTasks() {
        User user = createTestUser();
        Task task = createTestTask(user);
        entityManager.flush();

        List<Task> tasks = taskRepository.findByUserIdAndDeletedFalse(user.getId());

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test Task");
        assertThat(tasks.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void findByIdAndUserIdAndDeletedFalseShouldReturnTask() {
        User user = createTestUser();
        Task task = createTestTask(user);
        entityManager.flush();

        Optional<Task> found = taskRepository.findByIdAndUserIdAndDeletedFalse(task.getId(), user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Task");
        assertThat(found.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void findByIdAndUserIdAndDeletedFalseShouldReturnEmptyForWrongUser() {
        User user1 = createTestUser();
        User user2 = new User();
        user2.setUsername("anotheruser");
        user2.setPassword("password");
        user2.setEmail("another@example.com");
        user2.setEnabled(true);
        user2 = entityManager.persist(user2);

        Task task = createTestTask(user1);
        entityManager.flush();

        Optional<Task> found = taskRepository.findByIdAndUserIdAndDeletedFalse(task.getId(), user2.getId());

        assertThat(found).isEmpty();
    }

    @Test
    void findByIdAndUserIdAndDeletedFalseShouldReturnEmptyForDeletedTask() {
        User user = createTestUser();
        Task task = createTestTask(user);
        task.setDeleted(true);
        entityManager.persist(task);
        entityManager.flush();

        Optional<Task> found = taskRepository.findByIdAndUserIdAndDeletedFalse(task.getId(), user.getId());

        assertThat(found).isEmpty();
    }

    @Test
    void softDeleteShouldMarkTaskAsDeleted() {
        User user = createTestUser();
        Task task = createTestTask(user);
        entityManager.flush();

        taskRepository.softDelete(task.getId());
        entityManager.flush();
        entityManager.clear();

        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertThat(deletedTask).isPresent();
        assertThat(deletedTask.get().isDeleted()).isTrue();
    }
}