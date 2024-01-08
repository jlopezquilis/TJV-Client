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
import java.util.List;

@ShellComponent
public class StudentShell {

    private final StudentService studentService;

    public StudentShell(StudentService studentService) {
        this.studentService = studentService;
    }

    @ShellMethod("Read a student by ID.")
    public String readStudent(@ShellOption int studentId) {
        StudentDto student = studentService.read(studentId);
        return student != null ? student.toString() : "Student with ID " + studentId + " not found.";
    }

    @ShellMethod("Create a new student.")
    public void createStudent(@ShellOption StudentDto studentDto) {
        studentService.create(studentDto);
        System.out.println("Created student: " + studentDto.toString());
    }

    @ShellMethod("Update the current student.")
    public void updateCurrentStudent(@ShellOption String name,
                                     @ShellOption Integer age,
                                     @ShellOption Collection<CourseDto> courses) {
        studentService.updateCurrentStudent(name, age, courses);
        System.out.println("Updated student.");
    }

    @ShellMethod("Delete the current student.")
    public void deleteCurrentStudent() {
        studentService.deleteCurrentStudent();
        System.out.println("Deleted current student.");
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
