package qa.udst.student_service.business;

import org.springframework.stereotype.Service;
import qa.udst.student_service.business.domain.Student;
import qa.udst.student_service.business.ports.StudentRepositoryPort;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepositoryPort studentRepositoryPort;

    public StudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
    }

    public List<Student> findAll() {
        return studentRepositoryPort.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepositoryPort.findById(id);
    }

    public Optional<Student> findByStudentId(String studentId) {
        return studentRepositoryPort.findByStudentId(studentId);
    }

    public Student create(Student student) {
        return studentRepositoryPort.save(student);
    }

    public Student update(Long id, Student updated) {
        Student existing = studentRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setMajor(updated.getMajor());
        return studentRepositoryPort.save(existing);
    }

    public void delete(Long id) {
        studentRepositoryPort.deleteById(id);
    }
}