package ua.berlinets.s28359.TPO_PRO.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.berlinets.s28359.TPO_PRO.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();

    Optional<User> findByUsername(String username);
}
