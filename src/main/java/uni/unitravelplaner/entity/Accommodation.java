package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;

@Entity
@Table(name = "accommodations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Accommodation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private String link;

    private ZonedDateTime begin;
    private ZonedDateTime end;

    private Float price;

    @ColumnDefault("false")
    private boolean isBreakfastIncluded = false;

    @ManyToOne(optional = false)
    private Trip trip;
}
