package qa.udst.course_service.business.ports;

import qa.udst.course_service.business.domain.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepositoryPort {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Optional<Course> findByCourseCode(String courseCode);
    Course save(Course course);
    void deleteById(Long id);
}