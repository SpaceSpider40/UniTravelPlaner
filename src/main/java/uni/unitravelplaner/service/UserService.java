package uni.unitravelplaner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uni.unitravelplaner.dto.user.UserCreationDto;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> getUserPage(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public User createUser(UserCreationDto dto) {

        if (dto.username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (userRepository.findUserByUsername(dto.username).isPresent()) {
            throw new IllegalArgumentException("Username taken");
        }

        final User user = User.builder()
                .username(dto.username)
                .created(Instant.now().atZone(ZoneId.systemDefault()))
                .build();

        userRepository.save(user);

        return user;
    }

}
