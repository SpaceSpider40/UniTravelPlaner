package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.entity.Attendee;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.enums.AttendeeStatus;
import uni.unitravelplaner.repository.AttendeeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;

    public Page<Attendee> getAttends(User user, Pageable pageable){
        return attendeeRepository.findAllByUser(user, pageable);
    }

    public Page<Attendee> getAttends(User user, AttendeeStatus attendeeStatus, Pageable pageable) {
        return attendeeRepository.findAllByUserAndStatus(user, attendeeStatus, pageable);
    }

    public Attendee acceptAttendance(Long aId) {
        final Attendee attendee = attendeeRepository.findById(aId)
                                                     .orElseThrow();
        attendee.setStatus(AttendeeStatus.ACCEPTED);

        attendeeRepository.save(attendee);

        return attendee;
    }

    public List<Attendee> getAttendsForUser(User user) {
        return attendeeRepository.findAllByUser(user);
    }
}
