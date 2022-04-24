package by.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import by.restaurantvoting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}