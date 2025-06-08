package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "activities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private String address;
    private String link;

    private ZonedDateTime start;
    @Column(name = "_end")
    private ZonedDateTime end;

    private Float cost;
    @ManyToOne(optional = false)
    private Trip trip;
}
