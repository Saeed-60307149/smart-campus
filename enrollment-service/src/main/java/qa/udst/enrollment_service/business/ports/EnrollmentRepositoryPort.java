package qa.udst.enrollment_service.business.ports;

import qa.udst.enrollment_service.business.domain.Enrollment;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepositoryPort {
    List<Enrollment> findAll();
    Optional<Enrollment> findById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    Enrollment save(Enrollment enrollment);
}