package cz.cvut.fit.tjv.project.tjvclient.api_client;

import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class StudentClient {
    private RestClient studentRestClient;
    private RestClient currentStudentRestClient;
    private String baseUrl;

    public StudentClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl + "/student";
        studentRestClient = RestClient.create(this.baseUrl);
    }

    public void setCurrentStudent(int studentId) {
        currentStudentRestClient = RestClient
                .builder()
                .baseUrl(baseUrl + "/{id}")
                .defaultUriVariables(Map.of("id", studentId))
                .build();
    }

    public void updateCurrentStudent(StudentDto data) {
        currentStudentRestClient
                .put()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Collection<StudentDto> readAll() {
        return Arrays.asList(studentRestClient.get()
                .retrieve()
                .toEntity(StudentDto[].class)
                .getBody());
    }

    public void create(StudentDto data) {
        studentRestClient.post()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public List<StudentDto> readByCoursesId(int courseId) {
        return Arrays.asList(studentRestClient.get()
                .uri("/" + courseId + "/getStudentsByCourseId")
                .retrieve()
                .toEntity(StudentDto[].class)
                .getBody());
    }

    public Integer obtainTotalEnrolledCredits(int studentId) {
        return studentRestClient.get()
                .uri("/" + studentId + "/obtainTotalEnrolledCredits")
                .retrieve()
                .toEntity(Integer.class)
                .getBody();
    }
}

