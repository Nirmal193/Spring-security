package com.example.security.Student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> students = Arrays.asList(
            new Student(1,"Nirmal"),
            new Student(2,"Nihar"),
            new Student(3,"Swagat")
    );
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINE')")
    public List<Student> getStudents(){
        return students;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        //students.add(student);
        System.out.println("registerNewStudent");
        System.out.println(student.getName() + " " +student.getStudentID());
    }
    @DeleteMapping(path = "{studentID}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentID") Integer studentID){
        //students.remove(studentID);
        System.out.println("deleteStudent");
        System.out.println(studentID);
    }
    @PutMapping(path = "{studentID}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudents(@PathVariable("studentID") Integer studentID,@RequestBody Student student){
        //students.remove(studentID);
        //students.add(new Student(student.getStudentID(),student.getName()));
        System.out.println("updateStudents");
        System.out.println(student.getName() + " with id " + student.getStudentID());
    }
}
