package cz.cvut.fit.tjv.project.tjvclient.shell;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import cz.cvut.fit.tjv.project.tjvclient.service.StudentService;
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
public class StudentShell {

    private final StudentService studentService;

    public StudentShell(StudentService studentService) {
        this.studentService = studentService;
    }

    @ShellMethod("List all students.")
    public Collection<StudentDto> listAllStudents() {
        return studentService.readAll();
    }

    @ShellMethod("Read a specific student by ID. After the read, this student will become the selected one.")
    public String readStudent(@ShellOption int studentId) {
        StudentDto student = studentService.read(studentId);
        return student != null ? student.toString() : "Student with ID " + studentId + " not found.";
    }

    @ShellMethod("Set the selected student by its ID.")
    public void setCurrentStudent(@ShellOption int studentId) {
        studentService.setCurrentStudent(studentId);
    }

    @ShellMethod("Read the selected student.")
    public StudentDto readCurrentStudent() {
        return studentService.read(studentService.getCurrentStudent());
    }


    @ShellMethod("Create a new student.")
    public String createNewStudent(@ShellOption String name, @ShellOption int age) {
        var student = new StudentDto();
        student.setName(name);
        student.setAge(age);
        student.setCourses(Collections.emptyList());
        try {
            studentService.create(student);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Student created successfully";
    }

    @ShellMethod("Update the selected student.")
    public void updateCurrentStudent(@ShellOption String name,
                                     @ShellOption Integer age,
                                     @ShellOption Collection<CourseDto> courses) {
        studentService.updateCurrentStudent(name, age, courses);
        System.out.println("Updated student.");
    }

    @ShellMethod("Update the selected student name.")
    public String updateCurrentStudentName(@ShellOption String name) {
        if (!studentService.isCurrentStudentSet()) {
            return "No current student set. Please set student first.";
        }
        try {
            StudentDto current = readCurrentStudent();
            studentService.updateCurrentStudent(name, current.getAge(), current.getCourses());
            return "Student name updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the selected student age.")
    public String updateCurrentStudentAge(@ShellOption int age) {
        if (!studentService.isCurrentStudentSet()) {
            return "No current student set. Please set student first.";
        }
        try {
            StudentDto current = readCurrentStudent();
            studentService.updateCurrentStudent(current.getName(), age, current.getCourses());
            return "Student age updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Delete the current student.")
    public String deleteCurrentStudent() {
        if (!studentService.isCurrentStudentSet()) {
            return "No current student set. Please set a current student first.";
        }
        try {
            studentService.deleteCurrentStudent();
            return "Student deleted successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("List students by course ID.")
    public String listStudentsByCourseId(@ShellOption int courseId) {
        Collection<StudentDto> students = studentService.readByCoursesId(courseId);
        return students.stream()
                .map(StudentDto::toString)
                .reduce("", (partialString, student) -> partialString + student + System.getProperty("line.separator"));
    }

    @ShellMethod("Get the total enrolled credits for a student.")
    public String getTotalEnrolledCredits(@ShellOption int studentId) {
        Integer totalCredits = studentService.obtainTotalEnrolledCredits(studentId);
        return "Total enrolled credits for student with ID " + studentId + ": " + totalCredits;
    }

    // Additional shell methods can be added here as needed.
}
