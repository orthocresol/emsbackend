package com.asif.ems.controller;

import com.asif.ems.entities.Role;
import com.asif.ems.entities.User;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("lock/{id}")
    public ResponseEntity<String> lockUser(@PathVariable Integer id){
        Optional<User> user = repository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("LOCK");
        repository.save(nonNulluser);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("unlock/{id}")
    public ResponseEntity<String> unlockUser(@PathVariable Integer id){
        Optional<User> user = repository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("UNLOCK");
        repository.save(nonNulluser);
        return ResponseEntity.ok("Success");
    }

}
