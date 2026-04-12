package qa.udst.course_service.infrastructure;

import org.springframework.stereotype.Repository;
import qa.udst.course_service.business.domain.Course;
import qa.udst.course_service.business.ports.CourseRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseJpaAdapter implements CourseRepositoryPort {

    private final SpringDataCourseRepository jpaRepository;

    public CourseJpaAdapter(SpringDataCourseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private CourseEntity toEntity(Course course) {
        CourseEntity entity = new CourseEntity();
        entity.setId(course.getId());
        entity.setCourseCode(course.getCourseCode());
        entity.setTitle(course.getTitle());
        entity.setInstructor(course.getInstructor());
        entity.setCapacity(course.getCapacity());
        entity.setEnrolled(course.getEnrolled());
        entity.setSchedule(course.getSchedule());
        return entity;
    }

    private Course toDomain(CourseEntity entity) {
        Course course = new Course();
        course.setId(entity.getId());
        course.setCourseCode(entity.getCourseCode());
        course.setTitle(entity.getTitle());
        course.setInstructor(entity.getInstructor());
        course.setCapacity(entity.getCapacity());
        course.setEnrolled(entity.getEnrolled());
        course.setSchedule(entity.getSchedule());
        return course;
    }

    @Override
    public List<Course> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Course> findByCourseCode(String courseCode) {
        return jpaRepository.findByCourseCode(courseCode).map(this::toDomain);
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = toEntity(course);
        CourseEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}