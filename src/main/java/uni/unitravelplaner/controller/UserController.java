package uni.unitravelplaner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.unitravelplaner.dto.car.CarCreationDto;
import uni.unitravelplaner.dto.user.UserCreationDto;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.enums.AttendeeStatus;
import uni.unitravelplaner.service.AttendeeService;
import uni.unitravelplaner.service.CarService;
import uni.unitravelplaner.service.TripService;
import uni.unitravelplaner.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final CarService carService;
    private final TripService tripService;
    private final AttendeeService attendeeService;

    @PostMapping({"", "/"})
    public ResponseEntity<User> postUser(@RequestBody UserCreationDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PostMapping("/{uId}/attend/{aId}/accept")
    public ResponseEntity<Attendee> acceptInvite(@PathVariable Long uId, @PathVariable Long aId) {

        final var user = userService.getUser(uId);

        final var attends = attendeeService.getAttendsForUser(user);

        if (attends.stream().noneMatch(a -> a.getId().equals(aId))){
            throw new IllegalArgumentException("user does not have an attendance with id " + aId);
        }

        return ResponseEntity.ok(attendeeService.acceptAttendance(aId));
    }

    @GetMapping("")
    public Page<User> getUserPage(Pageable pageable) {
        return userService.getUserPage(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{id}/cars")
    public Page<Car> getCars(@PathVariable Long id, Pageable pageable)
    {
        return carService.getCarPageForUser(id, pageable);
    }

    @GetMapping("/{id}/trips")
    public Page<Trip> getTripsPage(@PathVariable Long id, Pageable pageable){
        return tripService.getTripsPageForUser(id, pageable);
    }

    @GetMapping("/{id}/attendee")
    public Page<Attendee> getAttendeePage(@PathVariable Long id,@RequestParam(required = false) AttendeeStatus attendeeStatus, Pageable pageable){

        if (attendeeStatus != null) {
            return attendeeService.getAttends(userService.getUser(id), attendeeStatus, pageable);
        }

        return attendeeService.getAttends(userService.getUser(id), pageable);
    }

}
