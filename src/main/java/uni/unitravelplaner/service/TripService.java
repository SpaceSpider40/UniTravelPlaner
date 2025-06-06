package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.dto.trip.TripCreationDto;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.enums.AttendeeStatus;
import uni.unitravelplaner.repository.TripRepository;

import java.time.Instant;

@Service
@AllArgsConstructor
public class TripService {

    private final CarService carService;
    private final UserService userService;

    private final TripRepository tripRepository;

    public Trip getTrip(Long id) {
        return tripRepository.findById(id)
                             .orElse(null);
    }

    public Page<Trip> getTripPage(int page, int size) {
        return tripRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size));
    }

    public Page<Trip> getTripPage(PageRequest pageRequest) {
        return tripRepository.findAll(pageRequest);
    }

    public Trip createTrip(TripCreationDto dto) {
        final User organizer = userService.getUser(dto.organizerId);

        if (organizer == null) throw new IllegalArgumentException("Organizer not found");

        if (dto.title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");

        if (dto.startDate.isBefore(Instant.now()
                                          .atZone(dto.startDate.getZone())))
            throw new IllegalArgumentException("Start date cannot be in the past");
        if (dto.startDate.isAfter(dto.endDate))
            throw new IllegalArgumentException("Start date cannot be after end date");

        final Trip trip = Trip.builder()
                              .title(dto.title)
                              .description(dto.description)
                              .startDate(dto.startDate)
                              .endDate(dto.endDate)
                              .organizer(organizer)
                              .build();

        tripRepository.save(trip);

        return trip;
    }

    public Attendee inviteUser(Long tripId, Long userId) {
        final Trip trip = tripRepository.findById(tripId)
                                        .orElseThrow();
        final User user = userService.getUser(userId);

        final var newAttendee = Attendee.builder()
                                     .user(user)
                .status(AttendeeStatus.INVITED)
                                     .build();

        final var attendees = trip.getAttendees();
        attendees.add(newAttendee);

        tripRepository.saveAndFlush(trip);

        return newAttendee;
    }

    public Page<Trip> getTripsPageForUser(Long id, Pageable pageable) {
        final var user = userService.getUser(id);

        return tripRepository.findAllByOrganizer(user, pageable);
    }
}
