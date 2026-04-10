package qa.udst.enrollment_service.business;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qa.udst.enrollment_service.business.domain.Enrollment;
import qa.udst.enrollment_service.business.domain.EnrollmentStatus;
import qa.udst.enrollment_service.config.WebClientConfig;
import qa.udst.enrollment_service.dto.CourseResponse;
import qa.udst.enrollment_service.dto.EnrollmentRequest;
import qa.udst.enrollment_service.dto.StudentResponse;
import qa.udst.enrollment_service.repository.EnrollmentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate;
    private final WebClientConfig webClientConfig;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
            RestTemplate restTemplate,
            WebClientConfig webClientConfig) {
        this.enrollmentRepository = enrollmentRepository;
        this.restTemplate = restTemplate;
        this.webClientConfig = webClientConfig;
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Enrollment enroll(EnrollmentRequest request) {

        // Step 1: Validate student exists
        String studentUrl = webClientConfig.getStudentServiceUrl()
                + "/api/students/" + request.getStudentId();
        StudentResponse student;
        try {
            student = restTemplate.getForObject(studentUrl, StudentResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Student not found with id: " + request.getStudentId());
        }
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + request.getStudentId());
        }

        // Step 2: Validate course exists and has seats
        String courseUrl = webClientConfig.getCourseServiceUrl()
                + "/api/courses/" + request.getCourseId();
        CourseResponse course;
        try {
            course = restTemplate.getForObject(courseUrl, CourseResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Course not found with id: " + request.getCourseId());
        }
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + request.getCourseId());
        }

        // Step 3: Check duplicate enrollment
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                request.getStudentId(), request.getCourseId())) {
            throw new RuntimeException("Student is already enrolled in this course.");
        }

        // Step 4: Check capacity
        if (!course.hasAvailableSeats()) {
            throw new RuntimeException("Course " + course.getCourseCode() + " is full.");
        }

        // Step 5: Increment enrollment count in course-service
        String incrementUrl = webClientConfig.getCourseServiceUrl()
                + "/api/courses/" + request.getCourseId() + "/enroll";
        restTemplate.put(incrementUrl, null);

        // Step 6: Save enrollment
        Enrollment enrollment = new Enrollment(
                request.getStudentId(),
                request.getCourseId(),
                student.getFullName(),
                course.getCourseCode());
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment cancelEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        return enrollmentRepository.save(enrollment);
    }
}