package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.*;
import uni.unitravelplaner.enums.AttendeeStatus;

import java.util.Collection;

@Getter
@Entity
@Table(name = "attendees")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {
    @Id
    @GeneratedValue
    private Long id;

    private AttendeeStatus status = AttendeeStatus.INVITED;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}

