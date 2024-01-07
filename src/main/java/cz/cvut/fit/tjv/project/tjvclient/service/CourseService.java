package cz.cvut.fit.tjv.project.tjvclient.service;

import cz.cvut.fit.tjv.project.tjvclient.api_client.CourseClient;
import cz.cvut.fit.tjv.project.tjvclient.model.CourseDto;
import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CourseService {
    private CourseClient courseClient;
    private Integer currentCourseId;

    public CourseService(CourseClient courseClient) {
        this.courseClient = courseClient;
    }

    //CRUD: read all
    public Collection<CourseDto> readAll() {
        return courseClient.readAll();
    }

    //CRUD: Read by id
    public CourseDto read(int courseId) {
        this.setCurrentCourse(courseId);
        return courseClient.read(currentCourseId);
    }

    //CRUD: Create
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

    //CRUD: Update
    public void updateCurrentCourse(String name, int credits, int capacity, String teacherName, List<String> studentNames) {
        var u = new CourseDto();
        u.setId(currentCourseId);
        u.setName(name);
        u.setCredits(credits);
        u.setCapacity(capacity);
        u.setTeacherName(teacherName);
        u.setStudentNames(studentNames);
        courseClient.updateCurrentCourse(u);
    }

    //CRUD: Delete
    public void deleteCurrentCourse() {
        courseClient.delete(currentCourseId);
    }

    public List<CourseDto> readByStudentsId(int studentId) {
        return courseClient.readByStudentsId(studentId);
    }

    public List<CourseDto> readByCredits(int credits) {
        return courseClient.readByCredits(credits);
    }
}
