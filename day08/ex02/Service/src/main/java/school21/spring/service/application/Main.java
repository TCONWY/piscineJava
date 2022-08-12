package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        User us1 = new User(1L,"123");
        User us2 = new User(2L,"qwe");
        usersRepository.save(us1);
        usersRepository.save(us2);
        usersRepository.savePassword(us1, "qwe");
        usersRepository.savePassword(us2, "rty");
        System.out.println(usersRepository.findAll());


        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        User us3 = new User(3L,"234");
        User us4 = new User(4L,"432");
        usersRepository.save(us3);
        usersRepository.save(us4);
        usersRepository.savePassword(us3, "zxc");
        usersRepository.savePassword(us4, "asd");
        System.out.println(usersRepository.findAll());
    }
}
