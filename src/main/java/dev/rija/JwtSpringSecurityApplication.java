package dev.rija;

import dev.rija.dao.TaskRepository;
import dev.rija.entities.AppRole;
import dev.rija.entities.AppUser;
import dev.rija.entities.Task;
import dev.rija.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecurityApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        // ajout de users et roles
        accountService.saveUser(new AppUser(null, "admin", "1234", null));
        accountService.saveUser(new AppUser(null, "user", "1234", null));

        accountService.saveRole(new AppRole(null, "ADMIN"));
        accountService.saveRole(new AppRole(null, "USER"));

        accountService.addRoleToUser("admin", "ADMIN");
        accountService.addRoleToUser("admin", "USER");
        accountService.addRoleToUser("user", "USER");

        // ajout de tasks
        Stream.of("T1", "T2", "T3").forEach(t-> {
            taskRepository.save(new Task(null, t));
        });
        taskRepository.findAll().forEach(System.out::println);
    }
}
