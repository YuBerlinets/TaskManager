package ua.berlinets.s28359.TPO_PRO.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateTaskDTO;
import ua.berlinets.s28359.TPO_PRO.entities.Task;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;
import ua.berlinets.s28359.TPO_PRO.helperServices.TaskMapper;
import ua.berlinets.s28359.TPO_PRO.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task createTask(CreateTaskDTO taskDTO) {
        Task task = taskMapper.dtoToTask(taskDTO);
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public void takeTaskByDev(long id, User user) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        if (task == null)
            return;
        task.setStatus(TaskStatus.ONGOING);
        task.setWorkingOn(user);
        taskRepository.save(task);
    }

    public void markTaskAsDone(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
    }

}
