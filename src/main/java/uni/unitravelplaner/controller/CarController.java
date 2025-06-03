package uni.unitravelplaner.controller;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.unitravelplaner.dto.car.CarCreationDto;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.service.CarService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/car")
public class CarController {
    private final CarService carService;

    @PostMapping("")
    public ResponseEntity<Car> postCar(@RequestParam Long userId, @RequestBody CarCreationDto dto) {
        return ResponseEntity.ok(carService.createCar(userId, dto));
    }

    @GetMapping("")
    public Page<Car> getCarPage(@RequestParam int page, @RequestParam int size) {
        final var pageRequest = PageRequest.of(page, size);

        return carService.getCarPage(pageRequest);
    }
}