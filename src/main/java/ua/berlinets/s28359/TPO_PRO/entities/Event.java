package ua.berlinets.s28359.TPO_PRO.entities;

import jakarta.persistence.*;
import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.EventType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private long eventId;

    @Column(name = "eventType")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "description")
    private String description;
    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @ManyToMany(mappedBy = "events", fetch = FetchType.EAGER)
    @Column(name = "invitedPeople")
    private Set<User> invitedPeople;
}
