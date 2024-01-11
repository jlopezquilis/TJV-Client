package cz.cvut.fit.tjv.project.tjvclient.api_client;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class TeacherClient {
    private RestClient teacherRestClient;
    private RestClient currentTeacherRestClient;
    private String baseUrl;

    public TeacherClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl + "/teacher";
        teacherRestClient = RestClient.create(this.baseUrl);
    }

    public void setCurrentTeacher(int teacherId) {
        currentTeacherRestClient = RestClient
                .builder()
                .baseUrl(baseUrl + "/{id}")
                .defaultUriVariables(Map.of("id", teacherId))
                .build();
    }

    //CRUD: Update
    public void updateCurrentTeacher(TeacherDto data) {
        currentTeacherRestClient
                .put()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    //CRUD: Read all teachers
    public Collection<TeacherDto> readAll() {
        return Arrays.asList(teacherRestClient.get()
                .retrieve()
                .toEntity(TeacherDto[].class)
                .getBody());
    }

    //CRUD: Read by id
    public TeacherDto read(Integer teacherId) {
        this.setCurrentTeacher(teacherId);
        return currentTeacherRestClient.get()
                .retrieve()
                .toEntity(TeacherDto.class)
                .getBody();
    }

    //CRUD: Create
    public void create(TeacherDto data) {
        teacherRestClient.post()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    //CRUD: Delete
    public void delete(Integer teacherId) {
        this.setCurrentTeacher(teacherId);
        currentTeacherRestClient.delete()
                .retrieve()
                .toBodilessEntity();
    }

    public Collection<TeacherDto> readByDepartment(String departmentId) {
        return Arrays.asList(teacherRestClient.get()
                .uri("/" + departmentId + "/readByDepartment")
                .retrieve()
                .toEntity(TeacherDto[].class)
                .getBody());
    }

    public Collection<StudentDto> obtainStudentsTaughtByTeacher(int teacherId) {
        return Arrays.asList(teacherRestClient.get()
                .uri("/" + teacherId + "/obtainStudentsTaughtByTeacher")
                .retrieve()
                .toEntity(StudentDto[].class)
                .getBody());
    }

    public Collection<TeacherDto> readByName(String name) {
        return Arrays.asList(teacherRestClient.get()
                .uri("/idByName/" + name)
                .retrieve()
                .toEntity(TeacherDto[].class)
                .getBody());
    }
}
