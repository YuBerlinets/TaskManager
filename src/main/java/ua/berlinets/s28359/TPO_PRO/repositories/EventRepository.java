package ua.berlinets.s28359.TPO_PRO.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.berlinets.s28359.TPO_PRO.entities.Event;
import ua.berlinets.s28359.TPO_PRO.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Event> findAllByCreatedBy(User createdBy);

    List<Event> findAllByInvitedPeopleContains(User invitedPeople);
}
