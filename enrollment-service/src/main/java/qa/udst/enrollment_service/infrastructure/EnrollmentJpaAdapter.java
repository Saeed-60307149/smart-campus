package qa.udst.enrollment_service.infrastructure;

import org.springframework.stereotype.Repository;
import qa.udst.enrollment_service.business.domain.Enrollment;
import qa.udst.enrollment_service.business.domain.EnrollmentStatus;
import qa.udst.enrollment_service.business.ports.EnrollmentRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EnrollmentJpaAdapter implements EnrollmentRepositoryPort {

    private final SpringDataEnrollmentRepository jpaRepository;

    public EnrollmentJpaAdapter(SpringDataEnrollmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private EnrollmentEntity toEntity(Enrollment enrollment) {
        EnrollmentEntity entity = new EnrollmentEntity();
        entity.setId(enrollment.getId());
        entity.setStudentId(enrollment.getStudentId());
        entity.setCourseId(enrollment.getCourseId());
        entity.setStudentName(enrollment.getStudentName());
        entity.setCourseCode(enrollment.getCourseCode());
        entity.setStatus(enrollment.getStatus().name());
        entity.setEnrolledAt(enrollment.getEnrolledAt());
        return entity;
    }

    private Enrollment toDomain(EnrollmentEntity entity) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(entity.getId());
        enrollment.setStudentId(entity.getStudentId());
        enrollment.setCourseId(entity.getCourseId());
        enrollment.setStudentName(entity.getStudentName());
        enrollment.setCourseCode(entity.getCourseCode());
        enrollment.setStatus(EnrollmentStatus.valueOf(entity.getStatus()));
        enrollment.setEnrolledAt(entity.getEnrolledAt());
        return enrollment;
    }

    @Override
    public List<Enrollment> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Enrollment> findByStudentId(Long studentId) {
        return jpaRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> findByCourseId(Long courseId) {
        return jpaRepository.findByCourseId(courseId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId) {
        return jpaRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentEntity entity = toEntity(enrollment);
        EnrollmentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
}