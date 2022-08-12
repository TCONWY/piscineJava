package school21.spring.service.repositories;

import school21.spring.service.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional findByEmail(String email);
    String findPasswordById(Long id);
    void savePassword(User user, String password);
    void saveByEmail(String email, String password);
}
