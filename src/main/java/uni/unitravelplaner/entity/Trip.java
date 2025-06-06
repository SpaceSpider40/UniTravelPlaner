package uni.unitravelplaner.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "trips")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    private ZonedDateTime created;
    private ZonedDateTime updated;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonIgnoreProperties("organizedTrips")
    private User organizer;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("trip")
    private Set<Attendee> attendees;

    @ManyToMany
    @JoinTable(
            name = "trips_cars",
            joinColumns = @JoinColumn(
                    name = "trip_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "car_id"
            )
    )
    @JsonIgnoreProperties("trips")
    private Set<Car> cars;
}
