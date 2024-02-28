package com.asif.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/advisor")
public class AdvisorController {

    @Autowired
    private AdvisorRequestRepository repository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("sendrequest")
    public ResponseEntity<String> addRequest (@RequestBody AdvisorRequests request){
        AdvisorRequests advisorRequest = new AdvisorRequests();
        advisorRequest.setEmailStudent(request.getEmailStudent());
        advisorRequest.setEmailTeacher(request.getEmailTeacher());
        repository.save(advisorRequest);
        return ResponseEntity.ok("Success");

    }
    @GetMapping("sentrequest/{email}")
    public ResponseEntity<List<User>> getAllSendRequestList(@PathVariable String email) {
        List<AdvisorRequests> advisorRequests = repository.findByEmailStudent(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailTeacher();
            Optional<User> user  = userRepository.findByEmail(_email);
            finalResult.add(user.get());
        }
        return ResponseEntity.ok(finalResult);
    }
}
