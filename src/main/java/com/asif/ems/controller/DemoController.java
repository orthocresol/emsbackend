package com.asif.ems.controller;

import com.asif.ems.entities.Role;
import com.asif.ems.entities.User;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @Autowired
    UserRepository repository;


    @GetMapping("updateroleteacher/{id}")
    public ResponseEntity<String> updateRolesToTeacher(@PathVariable Integer id){
        Optional<User> user = repository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.TEACHER);
        repository.save(nonNullUser);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("updaterolestudent/{id}")
    public ResponseEntity<String> updateRolesToStudent(@PathVariable Integer id){
        Optional<User> user = repository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.STUDENT);
        repository.save(nonNullUser);
        return ResponseEntity.ok("Success");
    }
}
