package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.dto.car.CarCreationDto;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.repository.CarRepository;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserService userService;

    public Car getCar(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Page<Car> getCarPage(PageRequest pageRequest){
        return carRepository.findAll(pageRequest);
    }

    public Car createCar(Long ownerId, CarCreationDto dto){
        final User owner = userService.getUser(ownerId);

        if (owner == null) throw new IllegalArgumentException("Owner not found");

        if (dto.name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        if (dto.make.isBlank()) throw new IllegalArgumentException("Make cannot be blank");
        if (dto.model.isBlank()) throw new IllegalArgumentException("Model cannot be blank");

        if (carRepository.findCarByNameAndOwner(dto.name, owner).isPresent()) throw new IllegalArgumentException("Car already exists");

        final Car car = Car.builder()
                .name(dto.name)
                .model(dto.model)
                .make(dto.make)
                .owner(owner)
                .build();

        if (dto.fuelCapacity != null){
            car.setFuelCapacity(dto.fuelCapacity);
        }

        if (dto.fuelConsumption != null){
            car.setFuelConsumption(dto.fuelConsumption);
        }

        if (dto.seatCount != null){
            car.setSeats(dto.seatCount);
        }

        return carRepository.save(car);
    }
}
