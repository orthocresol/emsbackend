package com.asif.ems.controller;

import com.asif.ems.entities.User;
import com.asif.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public  ResponseEntity<User> getAUser(@PathVariable String email){
        return userService.getAUser(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody HashMap<String, String> action){
        return userService.updateUser(id,action);
    }
}
