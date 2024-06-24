package ua.berlinets.s28359.TPO_PRO.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.TaskStatus;
import ua.berlinets.s28359.TPO_PRO.enums.TaskType;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;
    @Column(name = "taskType")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "workingOn")
    private User workingOn;
}
