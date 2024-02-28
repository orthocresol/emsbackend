package com.asif.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserRepository repository;
    @GetMapping("getallusers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = repository.findByRole(Role.USER);
        return ResponseEntity.ok(users);
    }
    @GetMapping("getallstudents")
    public ResponseEntity<List<User>> getAllStudents(){
        List<User> users = repository.findByRole(Role.STUDENT);
        return ResponseEntity.ok(users);
    }

    @GetMapping("getallteachers")
    public ResponseEntity<List<User>> getAllTeachers(){
        List<User> users = repository.findByRole(Role.TEACHER);
        return ResponseEntity.ok(users);
    }

    @GetMapping("getalladmins")
    public ResponseEntity<List<User>> getAllAdmins(){
        List<User> users = repository.findByRole(Role.ADMIN);
        return ResponseEntity.ok(users);
    }


}
