package qa.udst.api_gateway;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${student.service.url}")
    private String studentServiceUrl;

    @Value("${course.service.url}")
    private String courseServiceUrl;

    @Value("${enrollment.service.url}")
    private String enrollmentServiceUrl;

    @RequestMapping("/api/students/**")
    public ResponseEntity<?> routeToStudentService(
            HttpMethod method,
            HttpServletRequest request) {
        return forward(studentServiceUrl, request, method);
    }

    @RequestMapping("/api/courses/**")
    public ResponseEntity<?> routeToCourseService(
            HttpMethod method,
            HttpServletRequest request) {
        return forward(courseServiceUrl, request, method);
    }

    @RequestMapping("/api/enrollments/**")
    public ResponseEntity<?> routeToEnrollmentService(
            HttpMethod method,
            HttpServletRequest request) {
        return forward(enrollmentServiceUrl, request, method);
    }

    private ResponseEntity<?> forward(String serviceUrl,
                                       HttpServletRequest request,
                                       HttpMethod method) {
        String targetUrl = serviceUrl + request.getRequestURI();
        if (request.getQueryString() != null) {
            targetUrl += "?" + request.getQueryString();
        }

        // Read body directly from request
        String body = "";
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            body = sb.toString();
        } catch (IOException e) {
            body = "";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(targetUrl, method, entity, Object.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service unavailable: " + e.getMessage());
        }
    }
}