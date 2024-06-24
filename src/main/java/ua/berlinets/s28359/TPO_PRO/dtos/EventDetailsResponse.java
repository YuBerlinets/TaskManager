package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDetailsResponse {
    private long eventId;
    private EventType eventType;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime creationDate;
    private String createdBy;
    private List<UserResponseDTO> invitedPeople;
}
