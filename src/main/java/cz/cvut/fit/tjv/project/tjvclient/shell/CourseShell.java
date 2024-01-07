package cz.cvut.fit.tjv.project.tjvclient.shell;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.service.CourseService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collection;
import java.util.List;


@ShellComponent
public class CourseShell {
    private CourseService courseService;

    public CourseShell(CourseService courseService) {
        this.courseService = courseService;
    }

    @ShellMethod("List all courses.")
    public Collection<CourseDto> listAllCourses() {
        return courseService.readAll();
    }

    @ShellMethod("Read a specific course by ID.")
    public CourseDto readCourse(int courseId) {
        return courseService.read(courseId);
    }

    @ShellMethod("Create a new course.")
    public String createNewCourse(String name, int credits, int capacity, String teacherName, List<String> studentNames) {
        var course = new CourseDto();
        course.setName(name);
        course.setCredits(credits);
        course.setCapacity(capacity);
        course.setTeacherName(teacherName);
        course.setStudentNames(studentNames);
        try {
            courseService.create(course);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Course created successfully";
    }

    @ShellMethod("Update the current course.")
    public String updateCourse(String name, int credits, int capacity, String teacherName, List<String> studentNames) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            courseService.updateCurrentCourse(name, credits, capacity, teacherName, studentNames);
            return "Course updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Delete the current course.")
    public String deleteCurrentCourse() {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            courseService.deleteCurrentCourse();
            return "Course deleted successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Set the current course by ID.")
    public void setCurrentCourse(int courseId) {
        courseService.setCurrentCourse(courseId);
    }

    // Additional methods for readByStudentsId and readByCredits
    //toString() method is called by Spring Shell, no need of explicitly calling it.
    @ShellMethod("Read courses enrolled for a given studentId")
    public List<CourseDto> readCoursesByStudentsId(int studentId) {
        return courseService.readByStudentsId(studentId);
    }

    @ShellMethod("Read courses with a concrete number of credits")
    public List<CourseDto> readCoursesByCredits(int credits) {
        return courseService.readByCredits(credits);
    }
}
