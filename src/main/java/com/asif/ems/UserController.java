package com.asif.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserRepository repository;
    @GetMapping("info/{email}")
    public ResponseEntity<User> getInfo (@PathVariable String email){
        Optional<User> user = repository.findByEmail(email);
        User nonNullUser = user.get();
        return ResponseEntity.ok(nonNullUser);
    }

    @PostMapping("updateprofile")
    public ResponseEntity<String> update(@RequestBody EditStudentProfile profile){
        Optional<User> user = repository.findByEmail(profile.getEmail());
        User nonNullUser = user.get();
        nonNullUser.setName(profile.getName());
        nonNullUser.setPhone(profile.getPhone());
        nonNullUser.setDept(profile.getDept());
        nonNullUser.setStudentID(profile.getStudentID());
        nonNullUser.setBatch(profile.getBatch());
        repository.save(nonNullUser);
        return ResponseEntity.ok("Success");
    }
}