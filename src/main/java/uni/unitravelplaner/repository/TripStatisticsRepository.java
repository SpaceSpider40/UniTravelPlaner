package uni.unitravelplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.statistics.TripStatistics;

import java.time.ZonedDateTime;

public interface TripStatisticsRepository extends JpaRepository<TripStatistics, Long> {
    TripStatistics findFirstByCreated(ZonedDateTime created);

    TripStatistics findFirstByOrderByCreatedDesc();
}
