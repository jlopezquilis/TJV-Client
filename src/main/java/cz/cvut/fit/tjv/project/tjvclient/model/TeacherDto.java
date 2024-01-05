package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.List;
import java.util.Objects;

public class TeacherDto {
    private int id;
    private String name;
    private String department;
    private List<String> courseNames; // Assuming you only want to display the names of the courses

    public TeacherDto() {
    }

    public TeacherDto(int id, String name, String department, List<String> courseNames) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        TeacherDto teacherDto = (TeacherDto) o;
        return id == teacherDto.id &&
                Objects.equals(name, teacherDto.name) &&
                Objects.equals(department, teacherDto.department) &&
                Objects.equals(courseNames, teacherDto.courseNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, courseNames);
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", courseNames=" + courseNames +
                '}';
    }
}
