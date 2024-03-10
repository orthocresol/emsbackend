package com.asif.ems.controller;

import com.asif.ems.dto.StudentProfile;
import com.asif.ems.dto.TeacherProfile;
import com.asif.ems.entities.AcceptedPair;
import com.asif.ems.entities.AdvisorRequest;
import com.asif.ems.entities.User;
import com.asif.ems.repository.AcceptedPairRepository;
import com.asif.ems.repository.AdvisorRequestRepository;
import com.asif.ems.repository.UserRepository;
import com.asif.ems.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<TeacherProfile>> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherProfile> getInfo (@PathVariable UUID id){
        return teacherService.getInfo(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody TeacherProfile profile){
        return teacherService.update(id, profile);
    }

    @GetMapping("{email}/enrolled-students")
    public ResponseEntity<List<StudentProfile>> getEnrolledStudents(@PathVariable String email){
        return teacherService.getEnrolledStudents(email);
    }

    @GetMapping("{id}/requested-students")
    public ResponseEntity<List<StudentProfile>> getRequestedStudents(@PathVariable UUID id) {
        return teacherService.getRequestedStudents(id);
    }

    @PostMapping("{teacher-id}/requested-students")
    public ResponseEntity<String> acceptRequest (@RequestBody AcceptedPair request){
        return teacherService.acceptRequest(request);

    }

    @PutMapping("{teacher-id}/requested-students")
    public ResponseEntity<String> rejectRequest (@RequestBody AdvisorRequest request){
        return teacherService.rejectRequest(request);

    }
}
