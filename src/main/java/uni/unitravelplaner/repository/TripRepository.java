package uni.unitravelplaner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.entity.User;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findAllByOrganizer(User user, Pageable pageable);
}
