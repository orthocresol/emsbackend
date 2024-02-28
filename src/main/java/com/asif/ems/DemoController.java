package com.asif.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @Autowired
    UserRepository repository;
    @GetMapping("/okay")
    public ResponseEntity<String> sayHello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok("sex");
    }

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
