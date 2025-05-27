package ru.innopolis.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.model.Task;
import ru.innopolis.repository.TaskRepository;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String getAllTasks(Model model, Authentication authentication) {
        model.addAttribute("tasks", taskRepository.findAll());

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }

        return "tasks";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
}
