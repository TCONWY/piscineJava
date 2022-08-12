package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        usersRepository.save(new User(1L,"asd"));
        usersRepository.save(new User(2L,"qwe"));
        usersRepository = context.getBean("usersRepositoryTemplateJdbc", UsersRepository.class);
        System.out.println(usersRepository.findByEmail("qwe"));
    }
}
