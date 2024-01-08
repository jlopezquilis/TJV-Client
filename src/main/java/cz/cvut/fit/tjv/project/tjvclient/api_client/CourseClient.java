package cz.cvut.fit.tjv.project.tjvclient.api_client;

import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.TeacherDto;
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

    //CRUD: Update
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

    //CRUD: Read by id
    public CourseDto read(int courseId) {
        this.setCurrentCourse(courseId);
        return currentCourseRestClient.get()
                .retrieve()
                .toEntity(CourseDto.class)
                .getBody();
    }

    //CRUD: Create
    public void create(CourseDto data) {
        courseRestClient.post()
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    //CRUD: Delete
    public void delete(int courseId) {
        this.setCurrentCourse(courseId);
        currentCourseRestClient.delete()
                .retrieve()
                .toBodilessEntity();
    }

    public Collection<CourseDto> readByStudentsId(int studentId) {
        return Arrays.asList(courseRestClient.get()
                .uri("/" + studentId + "/readByStudentsId")
                .retrieve()
                .toEntity(CourseDto[].class)
                .getBody());
    }

    public Collection<CourseDto> readByCredits(int credits) {
        return Arrays.asList(courseRestClient.get()
                .uri("/" + credits + "/readByCredits")
                .retrieve()
                .toEntity(CourseDto[].class)
                .getBody());
    }
}
