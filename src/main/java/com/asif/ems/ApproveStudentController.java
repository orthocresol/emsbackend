package com.asif.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/oooooooooooooooooooooooooooooooooooooooooooooooooooooo")
public class ApproveStudentController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ApproveStudentsRepository repository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("add/{id}")
    public String addStudent(@PathVariable String id) {
        Optional<User> user = userRepository.findByEmail(id);
        String name = user.get().getName();


        ApprovedStudents student = new ApprovedStudents();
        student.setDept("");
        student.setName("");
        //repository.save(student);
        return name;
    }
}
