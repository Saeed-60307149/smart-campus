package main.java.qa.udst.student_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qa.udst.student_service.business.domain.Student;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
}