package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.entity.statistics.TripStatistics;
import uni.unitravelplaner.repository.TripStatisticsRepository;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final TripStatisticsRepository tripStatisticsRepository;

    public TripStatistics getCurrentTripStatistics(){
        return tripStatisticsRepository.findFirstByOrderByCreatedDesc();

    }
}
