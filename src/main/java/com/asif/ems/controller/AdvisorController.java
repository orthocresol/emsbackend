package com.asif.ems.controller;

import com.asif.ems.entities.AcceptedPair;
import com.asif.ems.entities.AdvisorRequests;
import com.asif.ems.entities.User;
import com.asif.ems.repository.AcceptedPairRepository;
import com.asif.ems.repository.AdvisorRequestRepository;
import com.asif.ems.repository.UserRepository;
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

    @Autowired
    private AcceptedPairRepository acceptedPairRepository;

    @GetMapping("findadvisor/{email}")
    public ResponseEntity<User> findAdvisor(@PathVariable String email){
        AcceptedPair acceptedPair = acceptedPairRepository.findByEmailStudent(email);
        Optional<User> user = userRepository.findByEmail(acceptedPair.getEmailTeacher());
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("enrolledstudents/{email}")
    public ResponseEntity<List<User>> getEnrolledStudents(@PathVariable String email){
        List<AcceptedPair> acceptedPairs = acceptedPairRepository.findByEmailTeacher(email);
        List<User> result = new ArrayList<>();
        for(int i = 0; i < acceptedPairs.size(); i++){
            AcceptedPair acceptedPair = acceptedPairs.get(i);
            Optional<User> user = userRepository.findByEmail(acceptedPair.getEmailStudent());
            result.add(user.get());
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("acceptrequest")
    public ResponseEntity<String> acceptRequest (@RequestBody AcceptedPair request){
        AcceptedPair acceptedPair = new AcceptedPair();
        acceptedPair.setEmailStudent(request.getEmailStudent());
        acceptedPair.setEmailTeacher(request.getEmailTeacher());
        acceptedPairRepository.save(acceptedPair);

        List<AdvisorRequests> advisorRequests = repository.findByEmailStudent(request.getEmailStudent());
        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advisorRequests1 = advisorRequests.get(i);
            repository.deleteById(advisorRequests1.getId());
        }
        return ResponseEntity.ok("Success");

    }

    @PostMapping("rejectrequest")
    public ResponseEntity<String> rejectRequest (@RequestBody AdvisorRequests request){
        List<AdvisorRequests> advisorRequests = repository.findByEmailTeacher(request.getEmailTeacher());

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advisorRequests1 = advisorRequests.get(i);
            if(advisorRequests1.getEmailStudent().equals(request.getEmailStudent())){
                repository.deleteById(advisorRequests1.getId());
            }
        }
        return ResponseEntity.ok("Success");

    }
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

    @GetMapping("getrequestedstudents/{email}")
    public ResponseEntity<List<User>> getRequestedStudents(@PathVariable String email) {
        List<AdvisorRequests> advisorRequests = repository.findByEmailTeacher(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailStudent();
            Optional<User> user  = userRepository.findByEmail(_email);
            finalResult.add(user.get());
        }
        return ResponseEntity.ok(finalResult);
    }

    @GetMapping("getavailableteachers/{email}")
    public ResponseEntity<List<User>> getAvailableTeachers(@PathVariable String email){
        List<AdvisorRequests> advisorRequests = repository.findByEmailStudent(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailTeacher();
            Optional<User> user  = userRepository.findByEmail(_email);
            finalResult.add(user.get());
        }

        List<User> listAll = userRepository.findAll();

        List<User> finale = new ArrayList<>();
        for(int i = 0; i < listAll.size(); i++){
            boolean mark = true;
            User user = listAll.get(i);
            for(int j = 0; j < finalResult.size(); j++){
                User insideUser = finalResult.get(j);
                if(user.getEmail().equals(insideUser.getEmail())){
                    mark = false;
                    break;
                }
            }
            if(mark){
                finale.add(user);
            }
        }
        return ResponseEntity.ok(finale);
    }
}
