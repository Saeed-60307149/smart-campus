package qa.udst.student_service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataStudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByStudentId(String studentId);
}