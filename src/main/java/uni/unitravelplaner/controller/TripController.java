package uni.unitravelplaner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.unitravelplaner.dto.trip.TripCreationDto;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.service.CarService;
import uni.unitravelplaner.service.TripService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    private final TripService tripService;
    private final CarService carService;

    @PostMapping({"", "/"})
    public ResponseEntity<Trip> postTrip(@RequestBody TripCreationDto dto) {
        return ResponseEntity.ok(tripService.createTrip(dto));
    }

    @PostMapping("{id}/invite")
    public ResponseEntity<Attendee> inviteUser(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(tripService.inviteUser(id, userId));
    }

    @GetMapping
    public Page<Trip> getTripPage(@RequestParam int page, @RequestParam int size,
                                  @RequestParam(defaultValue = "startDate") String sort,
                                  @RequestParam(defaultValue = "DESC") String direction)
    {

        final var pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        return tripService.getTripPage(pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTrip(id));
    }

    @GetMapping("/{tId}/cars")
    public Page<Car> getCarsPage(@PathVariable Long tId, Pageable pageable) {
        return tripService.getCarPageForTrip(tId, pageable);
    }

    @GetMapping("/{tId}/cars/assign/{cId}")
    public ResponseEntity<Trip> assignCar(@PathVariable Long tId, @PathVariable Long cId) {
        return ResponseEntity.ok(tripService.assignCar(tId, cId));
    }

    @GetMapping("/{tId}/cars/remove/{cId}")
    public ResponseEntity<Trip> removeCar(@PathVariable Long tId, @PathVariable Long cId) {
        return ResponseEntity.ok(tripService.removeCar(tId, cId));
    }
}
