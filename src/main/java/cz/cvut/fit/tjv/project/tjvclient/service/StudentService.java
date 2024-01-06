package cz.cvut.fit.tjv.project.tjvclient.service;


import cz.cvut.fit.tjv.project.tjvclient.api_client.StudentClient;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {
    private StudentClient studentClient;
    private Integer currentStudentId;

    public StudentService(StudentClient studentClient) {
        this.studentClient = studentClient;
    }

    /*
    public Collection<StudentDto> readAll() {
        return studentClient.readAll();
    }
     */

    //CRUD: Read by id

    public void create(StudentDto data) {
        studentClient.create(data);
    }

    public void setCurrentStudent(int studentId) {
        this.currentStudentId = studentId;
        studentClient.setCurrentStudent(studentId);
    }

    public boolean isCurrentStudentSet() {
        return currentStudentId != null;
    }

    public void updateCurrentStudent(StudentDto data) {
        if (isCurrentStudentSet()) {
            studentClient.updateCurrentStudent(data);
        } else {
            // Handle the case when no current student is set
        }
    }
}
