package ua.berlinets.s28359.TPO_PRO.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.berlinets.s28359.TPO_PRO.entities.Task;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);
}
