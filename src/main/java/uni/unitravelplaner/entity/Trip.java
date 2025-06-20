package uni.unitravelplaner.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import uni.unitravelplaner.enums.TripStatus;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("trip")
    private Set<Accommodation> accommodations;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("trip")
    private Set<Activity> activities;

    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.PENDING;

    public float getTotalCost() {
        final float[] cost = {0};

        if (accommodations != null) {
            accommodations.forEach(accommodation -> {
                Float price = accommodation.getPrice();
                if (price != null) {
                    cost[0] += price;
                }
            });
        }

        if (activities != null) {
            activities.forEach(activity -> {
                Float activityCost = activity.getCost();
                if (activityCost != null) {
                    cost[0] += activityCost;
                }
            });
        }

        return cost[0];
    }
}