package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Locale;
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
    private Set<Car> cars;

    @OneToMany(mappedBy = "user")
    private Set<Attendee> attendees;
}
