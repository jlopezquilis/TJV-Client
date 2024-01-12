package cz.cvut.fit.tjv.project.tjvclient.e2eTests;

import cz.cvut.fit.tjv.project.tjvclient.model.StudentDto;
import cz.cvut.fit.tjv.project.tjvclient.service.StudentService;
import cz.cvut.fit.tjv.project.tjvclient.shell.StudentShell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StudentE2ETest {

    @Autowired
    private StudentShell studentShell;
    private StudentService studentService;

    // Assuming this is an ID that will be used for tests
    private int testStudentId;

    @BeforeEach
    public void setup() {
        // Here you should create a student and store the ID for further tests

        studentShell.createNewStudent("Test Student", 20);
        Collection<StudentDto> studentList = studentShell.readStudentByName("Test Student");
        testStudentId = studentList.iterator().next().getId();
    }

    @Test
    public void testListAllStudents() {
        Collection<StudentDto> students = studentShell.listAllStudents();
        assertNotNull(students);
        assertFalse(students.isEmpty());
    }

    @Test
    public void testReadStudent() {
        String studentInfo = studentShell.readStudent(testStudentId);
        assertNotNull(studentInfo);
        assertTrue(studentInfo.contains("Test Student"));
    }

    @Test
    public void testSetAndReadCurrentStudent() {
        studentShell.setCurrentStudent(testStudentId);
        StudentDto currentStudent = studentShell.readCurrentStudent();
        assertNotNull(currentStudent);
        assertEquals(testStudentId, currentStudent.getId());
    }

    @Test
    public void testCreateNewStudent() {
        String result = studentShell.createNewStudent("New Student", 22);
        assertEquals("Student created successfully", result);
    }

    @Test
    public void testUpdateCurrentStudent() {
        studentShell.setCurrentStudent(testStudentId);
        studentShell.updateCurrentStudent("Updated Name", 21, Collections.emptyList());
        StudentDto updatedStudent = studentShell.readCurrentStudent();
        assertNotNull(updatedStudent);
        assertEquals("Updated Name", updatedStudent.getName());
        assertEquals(21, updatedStudent.getAge());
    }

}
