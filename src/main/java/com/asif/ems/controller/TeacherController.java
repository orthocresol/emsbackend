package com.asif.ems.controller;

import com.asif.ems.dto.TeacherProfile;
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
@RequestMapping("api/v1/teachers")
public class TeacherController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AcceptedPairRepository acceptedPairRepository;
    @Autowired
    private AdvisorRequestRepository advisorRequestRepository;

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<User>> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("{email}")
    public ResponseEntity<User> getInfo (@PathVariable String email){
        return teacherService.getInfo(email);
    }

    @PutMapping("{email}")
    public ResponseEntity<String> update(@RequestBody TeacherProfile profile){
        return teacherService.update(profile);
    }

    @GetMapping("{email}/enrolled-students")
    public ResponseEntity<List<User>> getEnrolledStudents(@PathVariable String email){
        return teacherService.getEnrolledStudents(email);
    }

    @GetMapping("{email}/requested-students")
    public ResponseEntity<List<User>> getRequestedStudents(@PathVariable String email) {
        return teacherService.getRequestedStudents(email);
    }

    @PostMapping("{teacher-id}/requested-students/{student-id}")
    public ResponseEntity<String> acceptRequest (@RequestBody AcceptedPair request){
        return teacherService.acceptRequest(request);

    }

    @PutMapping("{teacher-id}/requested-students/{student-id}")
    public ResponseEntity<String> rejectRequest (@RequestBody AdvisorRequests request){
        return teacherService.rejectRequest(request);

    }
}
