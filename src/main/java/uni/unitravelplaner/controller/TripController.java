package uni.unitravelplaner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.unitravelplaner.dto.trip.TripCreationDto;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.service.TripService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    private final TripService tripService;

    @PostMapping("")
    public ResponseEntity<Trip> postTrip(@RequestBody TripCreationDto dto) {
        return ResponseEntity.ok(tripService.createTrip(dto));
    }

    @PostMapping("{id}/invite")
    public ResponseEntity<Attendee> inviteUser(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(tripService.inviteUser(id, userId));
    }

    @GetMapping
    public Page<Trip> getTripPage(@RequestParam int page, @RequestParam int size) {
        return tripService.getTripPage(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTrip(id));
    }

}
