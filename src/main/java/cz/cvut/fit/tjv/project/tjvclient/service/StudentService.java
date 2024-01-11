package cz.cvut.fit.tjv.project.tjvclient.service;


import cz.cvut.fit.tjv.project.tjvclient.api_client.StudentClient;
import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private StudentClient studentClient;
    private Integer currentStudentId;

    public StudentService(StudentClient studentClient) {
        this.studentClient = studentClient;
    }

    public Collection<StudentDto> readAll() {
        return studentClient.readAll();
    }

    //CRUD: Read by id
    public StudentDto read(int studentId) {
        this.setCurrentStudent(studentId);
        return studentClient.read(currentStudentId);
    }

    //CRUD: Create
    public void create(StudentDto data) {
        studentClient.create(data);
    }

    public void setCurrentStudent(int studentId) {
        this.currentStudentId = studentId;
        studentClient.setCurrentStudent(studentId);
    }

    public int getCurrentStudent() {
        return this.currentStudentId;
    }

    public boolean isCurrentStudentSet() {
        return currentStudentId != null;
    }

    //CRUD: Update
    public void updateCurrentStudent(String name, Integer age, Collection<CourseDto> courses) {
        var u = new StudentDto();
        u.setId(currentStudentId);
        u.setName(name);
        u.setAge(age);
        u.setCourses(courses);
        studentClient.updateCurrentStudent(u);
    }

    //CRUD: Delete
    public void deleteCurrentStudent() {
        studentClient.delete(currentStudentId);
    }

    public Collection<StudentDto> readByCoursesId(int courseId) {
        return studentClient.readByCoursesId(courseId);
    }

    public Integer obtainTotalEnrolledCredits(int studentId) {
        return studentClient.obtainTotalEnrolledCredits(studentId);
    }
}
