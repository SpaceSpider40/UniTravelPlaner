package uni.unitravelplaner.schedule;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uni.unitravelplaner.entity.Trip;
import uni.unitravelplaner.entity.statistics.TripStatistics;
import uni.unitravelplaner.repository.TripRepository;
import uni.unitravelplaner.repository.TripStatisticsRepository;
import uni.unitravelplaner.service.TripService;

import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Component
@AllArgsConstructor
public class TripScheduleTasks {

    private final Logger logger = LoggerFactory.getLogger(TripScheduleTasks.class);

    private final TripRepository tripRepository;
    private final TripStatisticsRepository tripStatisticsRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void recordTripStatistics() {
        logger.info("Recording trip statistics...");

        final List<Trip> all = tripRepository.findAll();

        final TripStatistics tripStatistics = new TripStatistics();
        tripStatistics.setCreated(Instant.now().atZone(ZoneId.systemDefault()));

        logger.info("Found {} trips", all.size());

        tripStatistics.setTripsCount((long) all.size());

        logger.info("Recorded {} trips", tripStatistics.getTripsCount());

        try {
            all.forEach(trip -> {
                tripStatistics.setDaysCount(tripStatistics.getDaysCount() + Period.between(trip.getStartDate().toLocalDate(), trip.getEndDate().toLocalDate()).getDays());
                tripStatistics.setTotalCosts(tripStatistics.getTotalCosts() + trip.getTotalCost());
            });
        }catch (Exception e){
            logger.error("Error while recording trip statistics", e);
        }
        logger.info("Recorded trip statistics: {}", tripStatistics);

        tripStatisticsRepository.save(tripStatistics);
    }
}
