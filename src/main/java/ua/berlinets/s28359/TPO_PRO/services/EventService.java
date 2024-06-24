package ua.berlinets.s28359.TPO_PRO.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateEventDTO;
import ua.berlinets.s28359.TPO_PRO.dtos.EventDetailsResponse;
import ua.berlinets.s28359.TPO_PRO.entities.Event;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.helperServices.EventMapper;
import ua.berlinets.s28359.TPO_PRO.repositories.EventRepository;
import ua.berlinets.s28359.TPO_PRO.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    public Event saveEvent(CreateEventDTO createEventDTO, User createdBy) {
        Event event = eventMapper.requestToEvent(createEventDTO, createdBy);
        return eventRepository.save(event);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Set<User> getInvitedUsersByEventId(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null)
            return new HashSet<>();
        return event.getInvitedPeople();
    }

    public EventDetailsResponse getEventDetailsById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null)
            return null;
        return eventMapper.eventToResponse(event);
    }

    public List<EventDetailsResponse> findEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> foundEvents = eventRepository.findByEventDateBetween(startDate, endDate);
        List<EventDetailsResponse> response = new ArrayList<>();
        for (Event event : foundEvents) {
            response.add(eventMapper.eventToResponse(event));
        }
        return response;
    }

    public List<EventDetailsResponse> findEventsByCreator(User createdBy) {
        List<Event> foundEvents = eventRepository.findAllByCreatedBy(createdBy);
        List<EventDetailsResponse> response = new ArrayList<>();
        for (Event event : foundEvents) {
            response.add(eventMapper.eventToResponse(event));
        }
        return response;
    }

    public List<EventDetailsResponse> findEventsByInvitedUser(User user) {
        List<Event> foundEvents = eventRepository.findAllByInvitedPeopleContains(user);
        List<EventDetailsResponse> response = new ArrayList<>();
        for (Event event : foundEvents) {
            response.add(eventMapper.eventToResponse(event));
        }
        return response;
    }

    public void deleteEventById(long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            for (User user : event.getInvitedPeople()) {
                user.getEvents().remove(event);
                userRepository.save(user);
            }

            eventRepository.delete(event);
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }
}
