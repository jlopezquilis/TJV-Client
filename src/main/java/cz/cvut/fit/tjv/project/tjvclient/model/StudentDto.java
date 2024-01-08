package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class StudentDto {
    private int id;
    private String name;
    private int age;
    private Collection<CourseDto> courses;

    public StudentDto() {
    }

    public StudentDto(int id, String name, int age, Collection<CourseDto> courses) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courses = courses;
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

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto studentDto = (StudentDto) o;
        return id == studentDto.id &&
                age == studentDto.age &&
                Objects.equals(name, studentDto.name) &&
                Objects.equals(courses, studentDto.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, courses);
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append("Student Details").append(lineSeparator);
        sb.append(String.format("%-15s: %s", "ID", id)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Name", name)).append(lineSeparator);
        sb.append(String.format("%-15s: %d", "Age", age)).append(lineSeparator);

        return sb.toString();
    }


    public Collection<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(Collection<CourseDto> courses) {
        this.courses = courses;
    }
}
