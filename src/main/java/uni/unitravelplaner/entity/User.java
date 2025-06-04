package uni.unitravelplaner.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Getter
@Table(name = "_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private ZonedDateTime created;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private Set<Car> cars;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Attendee> attendees;

    @OneToMany(mappedBy = "organizer")
    @JsonIgnoreProperties("organizer")
    private Set<Trip> organizedTrips;
}
