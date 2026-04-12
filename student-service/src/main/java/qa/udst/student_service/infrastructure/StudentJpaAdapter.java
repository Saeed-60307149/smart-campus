package qa.udst.student_service.infrastructure;

import org.springframework.stereotype.Repository;
import qa.udst.student_service.business.domain.Student;
import qa.udst.student_service.business.ports.StudentRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentJpaAdapter implements StudentRepositoryPort {

    private final SpringDataStudentRepository jpaRepository;

    public StudentJpaAdapter(SpringDataStudentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private StudentEntity toEntity(Student student) {
        StudentEntity entity = new StudentEntity();
        entity.setId(student.getId());
        entity.setFullName(student.getFullName());
        entity.setEmail(student.getEmail());
        entity.setStudentId(student.getStudentId());
        entity.setMajor(student.getMajor());
        return entity;
    }

    private Student toDomain(StudentEntity entity) {
        Student student = new Student();
        student.setId(entity.getId());
        student.setFullName(entity.getFullName());
        student.setEmail(entity.getEmail());
        student.setStudentId(entity.getStudentId());
        student.setMajor(entity.getMajor());
        return student;
    }

    @Override
    public List<Student> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Student> findByStudentId(String studentId) {
        return jpaRepository.findByStudentId(studentId).map(this::toDomain);
    }

    @Override
    public Student save(Student student) {
        StudentEntity entity = toEntity(student);
        StudentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}