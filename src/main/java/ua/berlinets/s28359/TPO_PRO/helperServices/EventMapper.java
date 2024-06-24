package ua.berlinets.s28359.TPO_PRO.helperServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateEventDTO;
import ua.berlinets.s28359.TPO_PRO.dtos.EventDetailsResponse;
import ua.berlinets.s28359.TPO_PRO.dtos.UserResponseDTO;
import ua.berlinets.s28359.TPO_PRO.entities.Event;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EventMapper {
    private final UserService userService;

    public Event requestToEvent(CreateEventDTO request, User createdBy) {
        Event event = new Event();
        event.setEventType(request.getEventType());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setCreationDate(LocalDateTime.now());
        event.setCreatedBy(createdBy);


        for (String username : request.getInvitedPeopleUsernames()) {

            userService.findByUsername(username).ifPresent(foundUser -> foundUser.getEvents().add(event));
        }

        return event;
    }

    public EventDetailsResponse eventToResponse(Event event) {
        EventDetailsResponse response = new EventDetailsResponse();
        response.setEventId(event.getEventId());
        response.setEventType(event.getEventType());
        response.setDescription(event.getDescription());
        response.setEventDate(event.getEventDate());
        response.setCreationDate(event.getCreationDate());
        response.setCreatedBy(event.getCreatedBy().getUsername());
        response.setInvitedPeople(new ArrayList<>());
        for (User user : event.getInvitedPeople()) {
            response.getInvitedPeople().add(new UserResponseDTO(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName()));
        }
        return response;
    }
}
