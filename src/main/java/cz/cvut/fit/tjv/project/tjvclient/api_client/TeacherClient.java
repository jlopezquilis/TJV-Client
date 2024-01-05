package cz.cvut.fit.tjv.project.tjvclient.api_client;

import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
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

    public void updateCurrentTeacher(TeacherDto data) {
        currentTeacherRestClient
                .put()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Collection<TeacherDto> readAll() {
        return Arrays.asList(teacherRestClient.get()
                .retrieve()
                .toEntity(TeacherDto[].class)
                .getBody());
    }

    public void create(TeacherDto data) {
        teacherRestClient.post()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }
}
