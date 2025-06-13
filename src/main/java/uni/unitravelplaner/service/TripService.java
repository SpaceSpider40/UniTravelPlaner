package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.dto.trip.TripAccommodationDto;
import uni.unitravelplaner.dto.trip.TripCreationDto;
import uni.unitravelplaner.dto.trip.TripEditionDto;
import uni.unitravelplaner.entity.*;
import uni.unitravelplaner.enums.AttendeeStatus;
import uni.unitravelplaner.enums.TripStatus;
import uni.unitravelplaner.repository.TripRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

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
                              .created(Instant.now()
                                              .atZone(ZoneId.systemDefault()))
                              .updated(Instant.now()
                                              .atZone(ZoneId.systemDefault()))
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

    public Page<Car> getCarPageForTrip(Long tId, Pageable pageable) {
        final var trip = tripRepository.findById(tId)
                                       .orElseThrow();

        return carService.getCarPageForTrip(trip, pageable);
    }

    public Trip assignCar(Long tId, Long cId) {
        final var trip = tripRepository.findById(tId)
                                       .orElseThrow();
        final var car = carService.getCar(cId);

        if (car == null) throw new IllegalArgumentException("Car not found");

        Set<Car> cars = trip.getCars();
        cars.add(car);

        trip.setCars(cars);

        tripRepository.save(trip);

        return trip;
    }

    public Trip removeCar(Long tId, Long cId) {
        final var trip = tripRepository.findById(tId)
                                       .orElseThrow();
        final var car = carService.getCar(cId);

        if (car == null) throw new IllegalArgumentException("Car not found");

        Set<Car> cars = trip.getCars();
        cars.remove(car);

        trip.setCars(cars);

        tripRepository.save(trip);

        return trip;
    }

    public Trip addAccommodation(Long id, TripAccommodationDto dto) {

        final var trip = tripRepository.findById(id)
                                       .orElseThrow();

        final var accommodation = Accommodation.builder()
                                               .name(dto.name)
                                               .description(dto.description)
                                               .address(dto.address)
                                               .city(dto.city)
                                               .country(dto.country)
                                               .link(dto.link)
                                               .begin(dto.begin)
                                               .end(dto.end)
                                               .trip(trip)
                                               .price(dto.price)
                                               .build();

        Set<Accommodation> accommodations = trip.getAccommodations();
        accommodations.add(accommodation);

        trip.setAccommodations(accommodations);

        tripRepository.save(trip);

        return trip;
    }

    public Activity addActivity(Long id, Activity activity) {
        final var trip = tripRepository.findById(id)
                                       .orElseThrow();

        Set<Activity> activities = trip.getActivities();
        activities.add(activity);

        trip.setActivities(activities);

        tripRepository.save(trip);

        return activity;
    }

    public Trip editTrip(TripEditionDto dto) {
        final var trip = getTrip(dto.getId());

        if (dto.getTitle() != null && !dto.getTitle()
                                          .isBlank()) {
            trip.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null && !dto.getDescription()
                                                .isBlank()) {
            trip.setDescription(dto.getDescription());
        }

        if (dto.getStartDate() != null && !dto.getStartDate()
                                              .isBefore(Instant.now()
                                                               .atZone(dto.getStartDate()
                                                                          .getZone()))) {
            trip.setStartDate(dto.getStartDate());

        }

        if (dto.getEndDate() != null && !dto.getEndDate()
                                            .isBefore(dto.getStartDate())) {
            trip.setEndDate(dto.getEndDate());
        }

        trip.setUpdated(Instant.now()
                               .atZone(ZoneId.systemDefault()));

        tripRepository.save(trip);

        return trip;
    }

    public Boolean cancelTrip(Long id) {
        final Trip trip = getTrip(id);

        trip.setStatus(TripStatus.CANCELLED);

        tripRepository.save(trip);

        return true;
    }

    public Trip startTrip(Long id) {
        final Trip trip = getTrip(id);

        trip.setStatus(TripStatus.ACTIVE);

        tripRepository.save(trip);

        return trip;
    }

    public Trip finishTrip(Long id) {
        final Trip trip = getTrip(id);
        trip.setStatus(TripStatus.FINISHED);
        tripRepository.save(trip);
        return trip;
    }
}
