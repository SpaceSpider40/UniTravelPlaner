package uni.unitravelplaner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Entity
@Table(name = "_trips")
public class Trip
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
