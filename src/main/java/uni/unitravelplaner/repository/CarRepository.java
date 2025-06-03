package uni.unitravelplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.User;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarByName(String name);
    Optional<Car> findCarByNameAndOwner(String name, User owner);
}
