package cz.cvut.fit.tjv.project.tjvclient.shell;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import cz.cvut.fit.tjv.project.tjvclient.service.CourseService;
import cz.cvut.fit.tjv.project.tjvclient.service.StudentService;
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
    private StudentService studentService;
    private final TeacherService teacherService; // Inject TeacherService

    public CourseShell(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
    //READ methods
    @ShellMethod("List all courses.")
    public Collection<CourseDto> listAllCourses() {
        return courseService.readAll();
    }

    @ShellMethod("Read a specific course by ID. After the read, this course will become the selected one.")
    public CourseDto readCourse(@ShellOption int courseId) {
        return courseService.read(courseId);
    }

    @ShellMethod("Set the selected course by its ID.")
    public void setCurrentCourse(@ShellOption int courseId) {
        courseService.setCurrentCourse(courseId);
    }


    @ShellMethod("Read the selected course.")
    public CourseDto readCurrentCourse() {
        return courseService.read(courseService.getCurrentCourse());
    }

    //CREATE method
    @ShellMethod("Create a new course.")
    public String createNewCourse(@ShellOption String name, @ShellOption int credits, @ShellOption int capacity) {
        var course = new CourseDto();
        course.setName(name);
        course.setCredits(credits);
        course.setCapacity(capacity);
        course.setTeacher(null);
        course.setStudents(Collections.emptyList());
        try {
            courseService.create(course);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Course created successfully";
    }

    //UPDATE methods
    @ShellMethod("Update the selected course.")
    public String updateCurrentCourse(@ShellOption String name, @ShellOption int credits, @ShellOption int capacity, @ShellOption TeacherDto teacher, @ShellOption Collection<StudentDto> students) {
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

    @ShellMethod("Update the selected course name.")
    public String updateCurrentCourseName(@ShellOption String name) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            courseService.updateCurrentCourse(name, current.getCredits(), current.getCapacity(), current.getTeacher(), current.getStudents());
            return "Course name updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the selected course credits.")
    public String updateCurrentCourseCredits(@ShellOption int credits) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            courseService.updateCurrentCourse(current.getName(), credits, current.getCapacity(), current.getTeacher(), current.getStudents());
            return "Course credits updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the selected course capacity.")
    public String updateCurrentCourseCapacity(@ShellOption int capacity) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            courseService.updateCurrentCourse(current.getName(), current.getCredits(), capacity, current.getTeacher(), current.getStudents());
            return "Course capacity updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the selected course teacher by its teacherId.")
    public String updateCurrentCourseTeacher(@ShellOption int teacherId) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            TeacherDto teacher = teacherService.read(teacherId);
            courseService.updateCurrentCourse(current.getName(), current.getCredits(), current.getCapacity(), teacher, current.getStudents());
            return "Course teacher updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Add students to the selected course.")
    public String addStudentToCurrentCourse(@ShellOption(arity = -1) int[] studentIds) {
        if (!courseService.isCurrentCourseSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            CourseDto current = readCurrentCourse();
            Collection<StudentDto> studentList = current.getStudents();

            for (int studentId : studentIds) {
                if (studentList.size() >= current.getCapacity()) {
                    return "Course reached its full capacity. Some students were not added.";
                }
                StudentDto student = studentService.read(studentId);
                if (!studentList.contains(student)) {
                    studentList.add(student);
                }
            }

            courseService.updateCurrentCourse(current.getName(), current.getCredits(), current.getCapacity(), current.getTeacher(), studentList);
            return "Course student list updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Delete the selected course.")
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

    @ShellMethod("Ask for the selected course.")
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
    public Collection<CourseDto> readCoursesByStudentsId(@ShellOption int studentId) {
        return courseService.readByStudentsId(studentId);
    }

    @ShellMethod("Read courses with a concrete number of credits")
    public Collection<CourseDto> readCoursesByCredits(@ShellOption int credits) {
        return courseService.readByCredits(credits);
    }

    @ShellMethod("Read course for a given name")
    public Collection<CourseDto> readCourseByName(@ShellOption String name) {
        return courseService.readByName(name);
    }
}
