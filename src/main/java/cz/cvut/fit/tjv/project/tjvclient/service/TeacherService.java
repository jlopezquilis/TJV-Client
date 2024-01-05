package cz.cvut.fit.tjv.project.tjvclient.service;

import cz.cvut.fit.tjv.project.tjvclient.api_client.TeacherClient;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class TeacherService {
    private TeacherClient teacherClient;
    private Integer currentTeacherId;

    public TeacherService(TeacherClient teacherClient) {
        this.teacherClient = teacherClient;
    }

    public Collection<TeacherDto> readAll() {
        return teacherClient.readAll();
    }

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

    public void updateCurrentTeacher(TeacherDto data) {
        if (isCurrentTeacherSet()) {
            teacherClient.updateCurrentTeacher(data);
        } else {
            // Handle the case when no current teacher is set
        }
    }
}
