package qa.udst.course_service.business.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Course code is required")
    private String courseCode;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Instructor is required")
    private String instructor;

    @Column(nullable = false)
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @Column(nullable = false)
    @NotBlank(message = "Schedule is required")
    private int enrolled;

    @Column(nullable = false)
    private String schedule;

    public Course() {
    }

    public Course(String courseCode, String title, String instructor, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.instructor = instructor;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public boolean hasAvailableSeats() {
        return enrolled < capacity;
    }

    public void incrementEnrolled() {
        this.enrolled++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}