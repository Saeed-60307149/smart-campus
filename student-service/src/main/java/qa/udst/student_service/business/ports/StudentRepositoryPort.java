package qa.udst.student_service.business.ports;

import qa.udst.student_service.business.domain.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepositoryPort {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Optional<Student> findByStudentId(String studentId);
    Student save(Student student);
    void deleteById(Long id);
}