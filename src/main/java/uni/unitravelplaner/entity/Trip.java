package uni.unitravelplaner.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
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

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @JsonIgnoreProperties("trip")
    private Set<Accommodation> accommodations;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @JsonIgnoreProperties("trip")
    private Set<Activity> activities;
}
