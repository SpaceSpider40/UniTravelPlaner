package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}

