package cz.cvut.fit.tjv.project.tjvclient.api_client;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Component
public class CourseClient {
    private RestClient courseRestClient;
    private RestClient currentCourseRestClient;
    private String baseUrl;

    public CourseClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl + "/course";
        courseRestClient = RestClient.create(this.baseUrl);
    }

    public void setCurrentCourse(int courseId) {
        currentCourseRestClient = RestClient
                .builder()
                .baseUrl(baseUrl + "/{id}")
                .defaultUriVariables(Map.of("id", courseId))
                .build();
    }

    public void updateCurrentCourse(CourseDto data) {
        currentCourseRestClient
                .put()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Collection<CourseDto> readAll() {
        return Arrays.asList(courseRestClient.get()
                .retrieve()
                .toEntity(CourseDto[].class)
                .getBody());
    }

    public void create(CourseDto data) {
        courseRestClient.post()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public List<CourseDto> readByStudentsId(int studentId) {
        return Arrays.asList(courseRestClient.get()
                .uri("/" + studentId + "/readByStudentsId")
                .retrieve()
                .toEntity(CourseDto[].class)
                .getBody());
    }

    public List<CourseDto> readByCredits(int credits) {
        return Arrays.asList(courseRestClient.get()
                .uri("/" + credits + "/readByCredits")
                .retrieve()
                .toEntity(CourseDto[].class)
                .getBody());
    }
}
