package qa.udst.course_service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataCourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCourseCode(String courseCode);
}