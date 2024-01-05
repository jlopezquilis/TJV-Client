package cz.cvut.fit.tjv.project.tjvclient.service;

import cz.cvut.fit.tjv.project.tjvclient.api_client.CourseClient;
import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class CourseService {
    private CourseClient courseClient;
    private Integer currentCourseId;

    public CourseService(CourseClient courseClient) {
        this.courseClient = courseClient;
    }

    public Collection<CourseDto> readAll() {
        return courseClient.readAll();
    }

    public void create(CourseDto data) {
        courseClient.create(data);
    }

    public void setCurrentCourse(int courseId) {
        this.currentCourseId = courseId;
        courseClient.setCurrentCourse(courseId);
    }

    public boolean isCurrentCourseSet() {
        return currentCourseId != null;
    }

    public void updateCurrentCourse(CourseDto data) {
        if (isCurrentCourseSet()) {
            courseClient.updateCurrentCourse(data);
        } else {
            // Handle the case when no current course is set
        }
    }
}
