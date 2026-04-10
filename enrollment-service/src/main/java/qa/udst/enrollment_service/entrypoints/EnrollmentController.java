package qa.udst.enrollment_service.entrypoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import qa.udst.enrollment_service.business.EnrollmentService;
import qa.udst.enrollment_service.business.domain.Enrollment;
import qa.udst.enrollment_service.dto.EnrollmentRequest;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getByStudent(@PathVariable Long studentId) {
        return enrollmentService.findByStudentId(studentId);
    }

    @PostMapping
    public ResponseEntity<?> enroll(@Valid @RequestBody EnrollmentRequest request) {
        try {
            Enrollment enrollment = enrollmentService.enroll(request);
            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelEnrollment(@PathVariable Long id) {
        try {
            Enrollment enrollment = enrollmentService.cancelEnrollment(id);
            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}