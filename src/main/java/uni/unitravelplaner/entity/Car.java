package uni.unitravelplaner.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String model;
    private String make;

    @Setter
    private Float fuelConsumption;
    @Setter
    private Float fuelCapacity;

    @Setter
    private Integer seats;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties("cars")
    private User owner;

    @ManyToMany(mappedBy = "cars")
    private Set<Trip> trips;
}
