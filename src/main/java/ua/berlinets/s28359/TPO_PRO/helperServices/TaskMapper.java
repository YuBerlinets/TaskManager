package ua.berlinets.s28359.TPO_PRO.helperServices;

import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateTaskDTO;
import ua.berlinets.s28359.TPO_PRO.entities.Task;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;

@Service
public class TaskMapper {
    public Task dtoToTask(CreateTaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskType(taskDTO.getTaskType());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setCreatedBy(taskDTO.getCreatedBy());
        task.setWorkingOn(null);
        return task;

    }
}
