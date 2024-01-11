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
import java.util.Collections;
import java.util.List;

@ShellComponent
public class TeacherShell {

    private final TeacherService teacherService;

    public TeacherShell(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @ShellMethod("List all teachers")
    public Collection<TeacherDto> listAllTeachers() {
        return teacherService.readAll();
    }
    @ShellMethod("Read a specific teacher by ID. After the read, this teacher will become the selected one.")
    public TeacherDto readTeacher(@ShellOption Integer teacherId) {
        return teacherService.read(teacherId);
    }

    @ShellMethod("Set the selected teacher by its ID.")
    public void setCurrentCourse(@ShellOption int teacherId) {
        teacherService.setCurrentTeacher(teacherId);
    }

    @ShellMethod("Read the selected teacher.")
    public TeacherDto readCurrentTeacher() {
        return teacherService.read(teacherService.getCurrentTeacher());
    }

    @ShellMethod("Create a new teacher.")
    public String createNewTeacher(@ShellOption String name, @ShellOption String department) {
        var teacher = new TeacherDto();
        teacher.setName(name);
        teacher.setDepartment(department);
        teacher.setCourses(Collections.emptyList());
        try {
            teacherService.create(teacher);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Teacher created successfully";
    }

    @ShellMethod("Update the selected teacher.")
    public void updateCurrentTeacher(@ShellOption String name,
                                     @ShellOption String department,
                                     @ShellOption Collection<CourseDto> courses) {
        teacherService.updateCurrentTeacher(name, department, courses);
        System.out.println("Updated teacher.");
    }

    @ShellMethod("Update the selected teacher name.")
    public String updateCurrentTeacherName(@ShellOption String name) {
        if (!teacherService.isCurrentTeacherSet()) {
            return "No current teacher set. Please set teacher first.";
        }
        try {
            TeacherDto current = readCurrentTeacher();
            teacherService.updateCurrentTeacher(name, current.getDepartment(), current.getCourses());
            return "Teacher name updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Update the selected teacher department.")
    public String updateCurrentTeacherDepartment(@ShellOption String department) {
        if (!teacherService.isCurrentTeacherSet()) {
            return "No current teacher set. Please set teacher first.";
        }
        try {
            TeacherDto current = readCurrentTeacher();
            teacherService.updateCurrentTeacher(current.getName(), department, current.getCourses());
            return "Course name updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod("Delete the current teacher.")
    public String deleteCurrentTeacher() {
        if (!teacherService.isCurrentTeacherSet()) {
            return "No current course set. Please set a current course first.";
        }
        try {
            teacherService.deleteCurrentTeacher();
            return "Teacher deleted successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
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
