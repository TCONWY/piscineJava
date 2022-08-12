package school21.spring.service.repositories;

import school21.spring.service.models.User;
import school21.spring.service.repositories.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional findByEmail(String email);
}
