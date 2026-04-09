package qa.udst.student_service.business;

import org.springframework.stereotype.Service;
import qa.udst.student_service.business.domain.Student;
import qa.udst.student_service.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Long id, Student updated) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setMajor(updated.getMajor());
        return studentRepository.save(existing);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}