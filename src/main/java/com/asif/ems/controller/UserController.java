package com.asif.ems.controller;

import com.asif.ems.entities.Role;
import com.asif.ems.entities.User;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository repository;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = repository.findByRole(Role.USER);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody HashMap<String, String> action){
        if(action.get("action").equals("lock")){
            lockUser(id);
        }
        else if(action.get("action").equals("unlock")){
            unlockUser(id);
        }
        else if(action.get("action").equals("update-role-to-teacher")){
            updateRolesToTeacher(id);
        }
        else if(action.get("action").equals("update-role-to-student")){
            updateRolesToStudent(id);
        }
        return ResponseEntity.ok("Success");
    }


    public void lockUser(Integer id){
        Optional<User> user = repository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("LOCK");
        repository.save(nonNulluser);
    }

    public void unlockUser(Integer id){
        Optional<User> user = repository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("UNLOCK");
        repository.save(nonNulluser);
    }

    public void updateRolesToTeacher(Integer id){
        Optional<User> user = repository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.TEACHER);
        repository.save(nonNullUser);
    }

    public void updateRolesToStudent(Integer id){
        Optional<User> user = repository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.STUDENT);
        repository.save(nonNullUser);
    }
}
