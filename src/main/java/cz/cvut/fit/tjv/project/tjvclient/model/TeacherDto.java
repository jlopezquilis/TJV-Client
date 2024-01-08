package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class TeacherDto {
    private int id;
    private String name;
    private String department;
    private Collection<CourseDto> courses; // Assuming you only want to display the names of the courses

    public TeacherDto() {
    }

    public TeacherDto(int id, String name, String department, Collection<CourseDto> courses) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto teacherDto = (TeacherDto) o;
        return id == teacherDto.id &&
                Objects.equals(name, teacherDto.name) &&
                Objects.equals(department, teacherDto.department) &&
                Objects.equals(courses, teacherDto.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, courses);
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append("Teacher Details").append(lineSeparator);
        sb.append(String.format("%-15s: %s", "ID", id)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Name", name)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Department", department)).append(lineSeparator);

        return sb.toString();
    }

    public Collection<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(Collection<CourseDto> courses) {
        this.courses = courses;
    }
}
