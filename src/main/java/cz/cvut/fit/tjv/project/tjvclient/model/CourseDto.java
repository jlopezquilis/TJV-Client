package cz.cvut.fit.tjv.project.tjvclient.model;

import java.util.Collection;
import java.util.Objects;
import java.util.List;

public class CourseDto {
    private int id;
    private String name;
    private int credits;
    private int capacity;
    private TeacherDto teacher; // Assuming you only want to display the teacher's name
    private Collection<StudentDto> students; // Assuming you only want to display student names

    public CourseDto() {
    }

    public CourseDto(int id, String name, int credits, int capacity, TeacherDto teacher, List<StudentDto> students) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.teacher = teacher;
        this.students = students;
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
                Objects.equals(teacher, courseDto.teacher) &&
                Objects.equals(students, courseDto.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credits, capacity, teacher, students);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        sb.append("Course Details").append(lineSeparator);
        sb.append(String.format("%-15s: %s", "ID", id)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Name", name)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Credits", credits)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Capacity", capacity)).append(lineSeparator);
        sb.append(String.format("%-15s: %s", "Teacher", teacher != null ? teacher.toString() : "None")).append(lineSeparator);

        sb.append("Students:").append(lineSeparator);
        if (students != null && !students.isEmpty()) {
            for (StudentDto student : students) {
                sb.append(student.toString()).append(lineSeparator);
            }
        } else {
            sb.append("    No students enrolled.").append(lineSeparator);
        }

        return sb.toString();
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
    }

    public Collection<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(Collection<StudentDto> students) {
        this.students = students;
    }
}
