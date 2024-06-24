package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;
import ua.berlinets.s28359.TPO_PRO.enums.TaskType;

@Data
public class CreateTaskDTO {
    private TaskType taskType;
    private String title;
    private String description;
    private User createdBy;
}
