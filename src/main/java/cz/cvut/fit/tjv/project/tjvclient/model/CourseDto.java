package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.Objects;
import java.util.List;

public class CourseDto {
    private int id;
    private String name;
    private int credits;
    private int capacity;
    private String teacherName; // Assuming you only want to display the teacher's name
    private List<String> studentNames; // Assuming you only want to display student names

    public CourseDto() {
    }

    public CourseDto(int id, String name, int credits, int capacity, String teacherName, List<String> studentNames) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.teacherName = teacherName;
        this.studentNames = studentNames;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return id == courseDto.id &&
                credits == courseDto.credits &&
                capacity == courseDto.capacity &&
                Objects.equals(name, courseDto.name) &&
                Objects.equals(teacherName, courseDto.teacherName) &&
                Objects.equals(studentNames, courseDto.studentNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credits, capacity, teacherName, studentNames);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", capacity=" + capacity +
                ", teacherName='" + teacherName + '\'' +
                ", studentNames=" + studentNames +
                '}';
    }
}
