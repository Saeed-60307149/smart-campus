package qa.udst.course_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.udst.course_service.business.CourseService;
import qa.udst.course_service.business.domain.Course;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initCourses(CourseService courseService) {
        return args -> {
            courseService.create(new Course("CS101", "Introduction to Programming", "Dr. Ahmed", 30, "Mon/Wed 9:00-10:30"));
            courseService.create(new Course("CS201", "Data Structures", "Dr. Sara", 25, "Tue/Thu 11:00-12:30"));
            courseService.create(new Course("SE301", "Software Architecture", "Dr. Tewfik", 20, "Mon/Wed 14:00-15:30"));
            System.out.println("Course Service: test data initialized.");
        };
    }
}