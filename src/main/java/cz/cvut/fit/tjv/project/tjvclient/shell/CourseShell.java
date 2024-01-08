package cz.cvut.fit.tjv.project.tjvclient.shell;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import cz.cvut.fit.tjv.project.tjvclient.service.CourseService;
import cz.cvut.fit.tjv.project.tjvclient.service.TeacherService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@ShellComponent
public class CourseShell {
    private CourseService courseService;
    private final TeacherService teacherService; // Inject TeacherService

    public CourseShell(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    //READ methods
    @ShellMethod("List all courses.")
    public Collection<CourseDto> listAllCourses() {
        return courseService.readAll();
    }

    @ShellMethod("Read a specific course by ID.")
    public CourseDto readCourse(int courseId) {
        return courseService.read(courseId);
    }

    @ShellMethod("Read a specific course by ID.")
    public CourseDto readCurrentCourse() {
        return courseService.read(courseService.getCurrentCourse());
    }

    //CREATE method
    @ShellMethod("Create a new course.")
    public String createNewCourse(String name, int credits, int capacity, int teacherId) {
        var course = new CourseDto();
        course.setName(name);
        course.setCredits(credits);
        course.setCapacity(capacity);
        course.setTeacher(teacherService.read(teacherId));
        course.setStudents(Collections.emptyList());
        try {
            courseService.create(course);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Course created successfully";
    }

    //UPDATE methods
    @ShellMethod("Update the current course.")
    public String updateCurrentCourse(String name, int credits, int capacity, TeacherDto teacher, Collection<StudentDto> students) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            courseService.updateCurrentCourse(name, credits, capacity, teacher, students);
            return "Course updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the current course.")
    public String updateCurrentCourseName(String name) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            courseService.updateCurrentCourse(current.getName(), current.getCredits(), current.getCapacity(), current.getTeacher(), current.getStudents());
            return "Course name updated successfully";
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

    @ShellMethod("Ask for the current course.")
    public String askCurrentCourse() {
        try {
            return "Current Course ID: " + courseService.getCurrentCourse();
        } catch (NullPointerException e) {
            return "No course selected";
        }


    }

    // Additional methods for readByStudentsId and readByCredits
    //toString() method is called by Spring Shell, no need of explicitly calling it.
    @ShellMethod("Read courses enrolled for a given studentId")
    public Collection<CourseDto> readCoursesByStudentsId(int studentId) {
        return courseService.readByStudentsId(studentId);
    }

    @ShellMethod("Read courses with a concrete number of credits")
    public Collection<CourseDto> readCoursesByCredits(int credits) {
        return courseService.readByCredits(credits);
    }
}
