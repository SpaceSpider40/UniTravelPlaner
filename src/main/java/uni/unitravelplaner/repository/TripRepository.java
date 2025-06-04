package uni.unitravelplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
