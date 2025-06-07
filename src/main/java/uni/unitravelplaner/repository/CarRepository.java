package uni.unitravelplaner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarByName(String name);
    Optional<Car> findCarByNameAndOwner(String name, User owner);

    Page<Car> findCarsByOwner(User owner, Pageable pageable);

    Page<Car> findCarsByTripsContaining(Set<Trip> trips, Pageable pageable);
}
