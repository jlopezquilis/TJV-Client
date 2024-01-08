package cz.cvut.fit.tjv.project.tjvclient.shell;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import cz.cvut.fit.tjv.project.tjvclient.service.TeacherService;
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
public class TeacherShell {

    private final TeacherService teacherService;

    public TeacherShell(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ShellMethod("Read a teacher by ID.")
    public String readTeacher(@ShellOption Integer teacherId) {
        TeacherDto teacher = teacherService.read(teacherId);
        return teacher != null ? teacher.toString() : "Teacher with ID " + teacherId + " not found.";
    }

    @ShellMethod("Create a new teacher.")
    public void createTeacher(@ShellOption TeacherDto teacherDto) {
        teacherService.create(teacherDto);
        System.out.println("Created teacher: " + teacherDto.toString());
    }

    @ShellMethod("Update the current teacher's information.")
    public void updateCurrentTeacher(@ShellOption String name,
                                     @ShellOption String department,
                                     @ShellOption Collection<CourseDto> courses) {
        teacherService.updateCurrentTeacher(name, department, courses);
        System.out.println("Updated teacher.");
    }

    @ShellMethod("Delete the current teacher.")
    public void deleteCurrentTeacher() {
        teacherService.deleteCurrentTeacher();
        System.out.println("Deleted teacher.");
    }

    @ShellMethod("List teachers by department ID.")
    public String listTeachersByDepartment(@ShellOption String departmentId) {
        Collection<TeacherDto> teachers = teacherService.readByDepartment(departmentId);
        return teachers.stream()
                .map(TeacherDto::toString)
                .reduce("", (partialString, teacher) -> partialString + teacher + System.getProperty("line.separator"));
    }

    @ShellMethod("Get students taught by a specific teacher.")
    public String getStudentsTaughtByTeacher(@ShellOption int teacherId) {
        Collection<StudentDto> students = teacherService.obtainStudentsTaughtByTeacher(teacherId);
        return students.stream()
                .map(StudentDto::toString)
                .reduce("", (partialString, student) -> partialString + student + System.getProperty("line.separator"));
    }

    // Additional shell methods can be added here as needed.

}
