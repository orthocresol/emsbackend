package com.asif.ems.controller;

import com.asif.ems.dto.StudentProfile;
import com.asif.ems.entities.AcceptedPair;
import com.asif.ems.entities.AdvisorRequests;
import com.asif.ems.entities.Role;
import com.asif.ems.entities.User;
import com.asif.ems.repository.AcceptedPairRepository;
import com.asif.ems.repository.AdvisorRequestRepository;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<User>> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{email}")
    public ResponseEntity<User> getInfo (@PathVariable String email){
        return studentService.getInfo(email);
    }

    @PutMapping("{email}")
    public ResponseEntity<String> update(@RequestBody StudentProfile profile){
        return studentService.update(profile);
    }

    @GetMapping("{email}/advisor")
    public ResponseEntity<User> findAdvisor(@PathVariable String email){
        return studentService.findAdvisor(email);
    }

    @GetMapping("{email}/requested-teachers")
    public ResponseEntity<List<User>> getAllSendRequestList(@PathVariable String email) {
        return studentService.getAllSendRequestList(email);
    }

    @PostMapping("{student-id}/available-teachers/{teacher-id}")
    public ResponseEntity<String> addRequest (@RequestBody AdvisorRequests request){
        return studentService.addRequest(request);

    }

    @GetMapping("{email}/available-teachers")
    public ResponseEntity<List<User>> getAvailableTeachers(@PathVariable String email){
        return studentService.getAvailableTeachers(email);
    }
}
