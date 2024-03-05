package com.asif.ems.controller;

import com.asif.ems.dto.StudentProfile;
import com.asif.ems.dto.TeacherProfile;
import com.asif.ems.entities.AdvisorRequest;
import com.asif.ems.entities.Teacher;
import com.asif.ems.entities.User;
import com.asif.ems.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{email}")
    public ResponseEntity<StudentProfile> getInfo (@PathVariable String email){
        return studentService.getInfo(email);
    }

    @PutMapping("{email}")
    public ResponseEntity<String> update(@RequestBody StudentProfile profile){
        return studentService.update(profile);
    }

    @GetMapping("{email}/advisor")
    public ResponseEntity<TeacherProfile> findAdvisor(@PathVariable String email){
        return studentService.findAdvisor(email);
    }

    @GetMapping("{email}/requested-teachers")
    public ResponseEntity<List<TeacherProfile>> getAllSendRequestList(@PathVariable String email) {
        return studentService.getAllSendRequestList(email);
    }

    @PostMapping("{student-id}/available-teachers/{teacher-id}")
    public ResponseEntity<String> addRequest (@RequestBody AdvisorRequest request){
        return studentService.addRequest(request);

    }

    @GetMapping("{email}/available-teachers")
    public ResponseEntity<List<TeacherProfile>> getAvailableTeachers(@PathVariable String email){
        return studentService.getAvailableTeachers(email);
    }
}
