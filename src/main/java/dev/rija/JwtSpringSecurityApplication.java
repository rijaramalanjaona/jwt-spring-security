package dev.rija;

import dev.rija.dao.TaskRepository;
import dev.rija.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository taskRepository;

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("T1", "T2", "T3").forEach(t-> {
            taskRepository.save(new Task(null, t));
        });
        taskRepository.findAll().forEach(System.out::println);
    }
}
