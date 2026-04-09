package qa.udst.student_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.udst.student_service.business.StudentService;
import qa.udst.student_service.business.domain.Student;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initStudents(StudentService studentService) {
        return args -> {
            studentService.create(new Student("Alice Johnson", "alice@udst.qa", "S001", "Computer Science"));
            studentService.create(new Student("Bob Smith", "bob@udst.qa", "S002", "Software Engineering"));
            studentService.create(new Student("Carol White", "carol@udst.qa", "S003", "Information Technology"));
            System.out.println("Student Service: test data initialized.");
        };
    }
}