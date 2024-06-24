package ua.berlinets.s28359.TPO_PRO.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateTaskDTO;
import ua.berlinets.s28359.TPO_PRO.entities.Task;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;
import ua.berlinets.s28359.TPO_PRO.services.TaskService;
import ua.berlinets.s28359.TPO_PRO.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskViewController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/tasks")
    public String tasks(@RequestParam(name = "status", required = false, defaultValue = "ongoing") String status, Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        boolean isAllowedToSeeTasks = authentication != null && userService.isUserAllowedToSeeTasks(authentication.getName());
        boolean isDev = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("DEV"));
        boolean isUserAllowedToCreateTasks = authentication != null && userService.isUserAllowedToCreateTasks(authentication.getName());

        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("isAllowedToSeeTasks", isAllowedToSeeTasks);
        model.addAttribute("isUserAllowedToCreateTasks", isUserAllowedToCreateTasks);
        model.addAttribute("isDev", isDev);

        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            taskStatus = TaskStatus.ONGOING;
        }

        List<Task> tasks = taskService.findByStatus(taskStatus);
        model.addAttribute("tasks", tasks);
        model.addAttribute("status", status);

        return "tasks";
    }

    @GetMapping("/tasks/create")
    public String showCreateTaskForm(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("createTaskDTO", new CreateTaskDTO());
        return "new-task";
    }

    @PostMapping("/tasks/create")
    public String createTask(CreateTaskDTO createTaskDTO, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName()).orElseThrow();
        createTaskDTO.setCreatedBy(currentUser);
        taskService.createTask(createTaskDTO);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/take")
    public String takeTask(@RequestParam long taskId, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElse(null);
        taskService.takeTaskByDev(taskId, user);
        return "redirect:/tasks?status=ongoing";
    }

    @PostMapping("/tasks/markAsDone")
    public String markTaskAsDone(@RequestParam long taskId, Authentication authentication) {
        Task task = taskService.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        User currentUser = userService.findByUsername(authentication.getName()).orElseThrow();
        if (task.getWorkingOn() != null && task.getWorkingOn().getUsername().equals(currentUser.getUsername())) {
            taskService.markTaskAsDone(taskId);
        }
        return "redirect:/tasks?status=done";
    }
}
