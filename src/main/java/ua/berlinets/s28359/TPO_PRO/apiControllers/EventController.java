package ua.berlinets.s28359.TPO_PRO.apiControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.berlinets.s28359.TPO_PRO.services.EventService;

@RestController
@RequestMapping("api/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvitedUsersByEventId(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventDetailsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}
