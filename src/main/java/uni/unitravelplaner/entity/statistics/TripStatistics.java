package uni.unitravelplaner.entity.statistics;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "trip_statistics")
@Data
public class TripStatistics{
    @Id
    @GeneratedValue
    private Long id;

    private ZonedDateTime created;

    private Long tripsCount = 0L;
    private Long daysCount = 0L;
    private Float totalCosts = 0f;
}
