package edu.school21.sockets.repositories;


import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional findByName(String name);
    String findPasswordById(Long id);
    void savePassword(User user, String password);
    void saveByEmail(String email, String password);
}
