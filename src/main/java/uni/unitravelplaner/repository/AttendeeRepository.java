package uni.unitravelplaner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.enums.AttendeeStatus;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Page<Attendee> findAllByUser(User user, Pageable pageable);

    Page<Attendee> findAllByUserAndStatus(User user, AttendeeStatus status, Pageable pageable);

   List<Attendee> findAllByUser(User user);
}
