package com.example.security.Student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> students =Arrays.asList(
            new Student(1,"Nirmal"),
            new Student(2,"Nihar"),
            new Student(3,"Swagat")
    );
    @GetMapping("{studentID}")
    public Student getStudent(@PathVariable("studentID") Integer studentID){
        return students.stream().filter(student -> studentID.equals(student.getStudentID()))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Student" + studentID + "does not exists"));

    }
}
