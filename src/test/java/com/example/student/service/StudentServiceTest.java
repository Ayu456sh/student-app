package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Rahul");
        student.setEmail("rahul@gmail.com");
        student.setCourse("Java");
    }

    @Test
    void saveStudentReturnsSavedStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertSame(student, result);
        assertEquals(1L, result.getId());
        assertEquals("Rahul", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
        assertEquals("Java", result.getCourse());
        verify(studentRepository).save(student);
    }

    @Test
    void getAllStudentsReturnsRepositoryResults() {
        Student secondStudent = new Student();
        secondStudent.setId(2L);
        secondStudent.setName("Priya");
        secondStudent.setEmail("priya@gmail.com");
        secondStudent.setCourse("Spring Boot");
        List<Student> students = List.of(student, secondStudent);
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertSame(students, result);
        assertEquals(2, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals("Priya", result.get(1).getName());
        verify(studentRepository).findAll();
    }

    @Test
    void getAllStudentsReturnsEmptyListWhenRepositoryIsEmpty() {
        when(studentRepository.findAll()).thenReturn(List.of());

        List<Student> result = studentService.getAllStudents();

        assertTrue(result.isEmpty());
        verify(studentRepository).findAll();
    }

    @Test
    void deleteStudentDelegatesToRepository() {
        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }
}
