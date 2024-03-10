package com.asif.ems.controller;

import com.asif.ems.entities.User;
import com.asif.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<User> getAUser(@PathVariable UUID id){
        return userService.getAUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody HashMap<String, String> action){
        return userService.updateUser(id,action);
    }
}
