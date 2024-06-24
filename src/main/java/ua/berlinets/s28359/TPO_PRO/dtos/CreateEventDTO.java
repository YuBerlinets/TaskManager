package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventDTO {
    private EventType eventType;
    private String description;
    private LocalDateTime eventDate;
    private List<String> invitedPeopleUsernames;
}
