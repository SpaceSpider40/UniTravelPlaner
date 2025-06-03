package uni.unitravelplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.unitravelplaner.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
}
