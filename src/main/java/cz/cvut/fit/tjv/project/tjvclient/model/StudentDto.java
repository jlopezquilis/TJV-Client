package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.List;
import java.util.Objects;

public class StudentDto {
    private int id;
    private String name;
    private int age;
    private List<String> courseNames;

    public StudentDto() {
    }

    public StudentDto(int id, String name, int age, List<String> courseNames) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courseNames = courseNames;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto studentDto = (StudentDto) o;
        return id == studentDto.id &&
                age == studentDto.age &&
                Objects.equals(name, studentDto.name) &&
                Objects.equals(courseNames, studentDto.courseNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, courseNames);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", courseNames=" + courseNames +
                '}';
    }
}
