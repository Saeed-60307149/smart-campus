package qa.udst.enrollment_service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataEnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByStudentId(Long studentId);
    List<EnrollmentEntity> findByCourseId(Long courseId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}