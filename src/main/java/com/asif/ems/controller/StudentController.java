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
import java.util.UUID;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentProfile> getInfo (@PathVariable UUID id){
        return studentService.getInfo(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody StudentProfile profile){
        return studentService.update(id, profile);
    }

    @GetMapping("{id}/advisor")
    public ResponseEntity<TeacherProfile> findAdvisor(@PathVariable UUID id){
        return studentService.findAdvisor(id);
    }

    @GetMapping("{id}/requested-teachers")
    public ResponseEntity<List<TeacherProfile>> getAllSendRequestList(@PathVariable UUID id) {
        return studentService.getAllSendRequestList(id);
    }

    @PostMapping("{student-id}/available-teachers")
    public ResponseEntity<String> addRequest (@RequestBody AdvisorRequest request){
        return studentService.addRequest(request);
    }

    @GetMapping("{id}/available-teachers")
    public ResponseEntity<List<TeacherProfile>> getAvailableTeachers(@PathVariable UUID id){
        return studentService.getAvailableTeachers(id);
    }
}
