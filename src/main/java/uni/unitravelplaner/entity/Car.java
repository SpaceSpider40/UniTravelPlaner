package uni.unitravelplaner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_cars")
@Getter
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String model;

    private float fuelConsumption;
    private float fuelCapacity;

    private int seats;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
