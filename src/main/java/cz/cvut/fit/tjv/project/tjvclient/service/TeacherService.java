package cz.cvut.fit.tjv.project.tjvclient.service;

import cz.cvut.fit.tjv.project.tjvclient.api_client.TeacherClient;
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
    /*
    public Collection<TeacherDto> readAll() {
        return teacherClient.readAll();
    }
     */

    //CRUD: Read by id
    public TeacherDto read() {
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

    public boolean isCurrentTeacherSet() {
        return currentTeacherId != null;
    }

    //CRUD: Update
    public void updateCurrentTeacher(String name, String department, List<String> courseNames) {
        var u = new TeacherDto();
        u.setId(currentTeacherId);
        u.setName(name);
        u.setDepartment(department);
        u.setCourseNames(courseNames);
        teacherClient.updateCurrentTeacher(u);
    }

    //CRUD: Delete
    public void deleteCurrentTeacher() {
        teacherClient.delete(currentTeacherId);
    }


}
