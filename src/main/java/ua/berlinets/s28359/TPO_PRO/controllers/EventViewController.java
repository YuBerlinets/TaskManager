package ua.berlinets.s28359.TPO_PRO.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateEventDTO;
import ua.berlinets.s28359.TPO_PRO.dtos.EventDetailsResponse;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.EventType;
import ua.berlinets.s28359.TPO_PRO.services.EventService;
import ua.berlinets.s28359.TPO_PRO.services.UserService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventViewController {
    private final EventService eventService;
    private final UserService userService;


    @GetMapping("/calendar")
    public String calendar(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        boolean isAllowedToSeeTasks = authentication != null && userService.isUserAllowedToSeeTasks(authentication.getName());
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("isAllowedToSeeTasks", isAllowedToSeeTasks);

        YearMonth yearMonth = YearMonth.now();
        int daysInMonth = yearMonth.lengthOfMonth();
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(i);
        }

        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        List<EventDetailsResponse> events = eventService.findEventsByDateRange(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));

        events.sort(Comparator.comparing(EventDetailsResponse::getEventDate));

        int firstDayOfWeek = (startOfMonth.getDayOfWeek().getValue() + 6) % 7;

        model.addAttribute("events", events);
        model.addAttribute("days", days);
        model.addAttribute("month", yearMonth.getMonth().toString());
        model.addAttribute("year", yearMonth.getYear());
        model.addAttribute("firstDayOfWeek", firstDayOfWeek);

        return "calendar";
    }


    @GetMapping("/createEvent")
    public String showCreateEventForm(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));

        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("createEventDTO", new CreateEventDTO());
        model.addAttribute("eventTypes", EventType.values());
        model.addAttribute("users", userService.findAll());
        return "create-event";
    }

    @PostMapping("/createEvent")
    public String createEvent(CreateEventDTO createEventDTO, Authentication authentication) {
        User createdBy = userService.findByUsername(authentication.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        eventService.saveEvent(createEventDTO, createdBy);

        return "redirect:/calendar";
    }

    @GetMapping("/events/{id}")
    public String getEventDetails(@PathVariable long id, Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        boolean isAllowedToSeeTasks = authentication != null && userService.isUserAllowedToSeeTasks(authentication.getName());
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("isAllowedToSeeTasks", isAllowedToSeeTasks);

        EventDetailsResponse event = eventService.getEventDetailsById(id);
        if (event == null) {
            return "redirect:/calendar";
        }
        model.addAttribute("event", event);
        System.out.println(event.getInvitedPeople());

        return "event-details";
    }

    @GetMapping("/manageEvents")
    public String manageEvents(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));

        List<EventDetailsResponse> events = eventService.findEventsByCreator(userService.findByUsername(authentication.getName()).orElseThrow());
        model.addAttribute("events", events);
        model.addAttribute("isAuthorized", isAuthorized);
        return "manage-events";
    }

    @PostMapping("/deleteEvent")
    public String deleteEvent(@RequestParam long eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/manageEvents";
    }

    @GetMapping("/events/by-user/{username}")
    public String getEventsByUser(@PathVariable String username, Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        boolean isAllowedToSeeTasks = authentication != null && userService.isUserAllowedToSeeTasks(authentication.getName());
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("isAllowedToSeeTasks", isAllowedToSeeTasks);

        List<EventDetailsResponse> events = eventService.findEventsByInvitedUser(userService.findByUsername(username).orElseThrow());
        model.addAttribute("events", events);
        return "users-events";
    }
}
