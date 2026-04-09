package qa.udst.course_service.business;

import org.springframework.stereotype.Service;
import qa.udst.course_service.business.domain.Course;
import qa.udst.course_service.repository.CourseRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course incrementEnrollment(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        if (!course.hasAvailableSeats()) {
            throw new RuntimeException("Course " + course.getCourseCode() + " is full.");
        }
        course.incrementEnrolled();
        return courseRepository.save(course);
    }
}