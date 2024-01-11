package cz.cvut.fit.tjv.project.tjvclient.service;

import cz.cvut.fit.tjv.project.tjvclient.api_client.TeacherClient;
import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TeacherService {
    private TeacherClient teacherClient;
    private Integer currentTeacherId;

    public TeacherService(TeacherClient teacherClient) {
        this.teacherClient = teacherClient;
    }

    //CRUD: Read all teachers
    public Collection<TeacherDto> readAll() {
        return teacherClient.readAll();
    }

    //CRUD: Read by id
    public TeacherDto read(Integer teacherId) {
        this.setCurrentTeacher(teacherId);
        return teacherClient.read(currentTeacherId);
    }

    //CRUD: Create
    public void create(TeacherDto data) {
        teacherClient.create(data);
    }

    public void setCurrentTeacher(int teacherId) {
        this.currentTeacherId = teacherId;
        teacherClient.setCurrentTeacher(teacherId);
    }

    public int getCurrentTeacher() {
        return  this.currentTeacherId;
    }

    public boolean isCurrentTeacherSet() {
        return currentTeacherId != null;
    }

    //CRUD: Update
    public void updateCurrentTeacher(String name, String department, Collection<CourseDto> courses) {
        var u = new TeacherDto();
        u.setId(currentTeacherId);
        u.setName(name);
        u.setDepartment(department);
        u.setCourses(courses);
        teacherClient.updateCurrentTeacher(u);
    }

    //CRUD: Delete
    public void deleteCurrentTeacher() {
        teacherClient.delete(currentTeacherId);
    }

    public Collection<TeacherDto> readByDepartment(String departmentId) {
        return teacherClient.readByDepartment(departmentId);
    }

    public Collection<StudentDto> obtainStudentsTaughtByTeacher(int teacherId){
        return teacherClient.obtainStudentsTaughtByTeacher(teacherId);
    }

    public Collection<TeacherDto> readByName(String name) {return teacherClient.readByName(name);}

    public Collection<CourseDto> readCoursesByTeacherId(int teacherId) {return teacherClient.readCoursesByTeacherId(teacherId);}

}
