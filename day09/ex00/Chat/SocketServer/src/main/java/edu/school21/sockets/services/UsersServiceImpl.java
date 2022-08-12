package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService{
    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    private UsersRepository repository;
    @Override
    public String signUp(String name, String password) {
        Optional<User> optionalUser = repository.findByName(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if (optionalUser.isPresent()){
            password = repository.findPasswordById(optionalUser.get().getId());
        }
        else {
            password = encoder.encode(password);
            System.out.println(password);
            repository.saveByEmail(name, password);
        }
        return password;
    }
}
