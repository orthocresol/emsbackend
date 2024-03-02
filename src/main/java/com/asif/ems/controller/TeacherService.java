package com.asif.ems.controller;

import com.asif.ems.dto.TeacherProfile;
import com.asif.ems.entities.AcceptedPair;
import com.asif.ems.entities.AdvisorRequests;
import com.asif.ems.entities.Role;
import com.asif.ems.entities.User;
import com.asif.ems.repository.AcceptedPairRepository;
import com.asif.ems.repository.AdvisorRequestRepository;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private AcceptedPairRepository acceptedPairRepository;
    @Autowired
    private AdvisorRequestRepository advisorRequestRepository;

    public ResponseEntity<List<User>> getAllTeachers(){
        List<User> users = repository.findByRole(Role.TEACHER);
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getInfo (String email){
        Optional<User> user = repository.findByEmail(email);
        User nonNullUser = user.get();
        return ResponseEntity.ok(nonNullUser);
    }

    public ResponseEntity<String> update(TeacherProfile profile){
        Optional<User> user = repository.findByEmail(profile.getEmail());
        User nonNullUser = user.get();
        nonNullUser.setName(profile.getName());
        nonNullUser.setPhone(profile.getPhone());
        nonNullUser.setFaculty(profile.getFaculty());
        nonNullUser.setDesignation(profile.getDesignation());
        repository.save(nonNullUser);
        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<List<User>> getEnrolledStudents(String email){
        List<AcceptedPair> acceptedPairs = acceptedPairRepository.findByEmailTeacher(email);
        List<User> result = new ArrayList<>();
        for(int i = 0; i < acceptedPairs.size(); i++){
            AcceptedPair acceptedPair = acceptedPairs.get(i);
            Optional<User> user = repository.findByEmail(acceptedPair.getEmailStudent());
            result.add(user.get());
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<User>> getRequestedStudents(String email) {
        List<AdvisorRequests> advisorRequests = advisorRequestRepository.findByEmailTeacher(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailStudent();
            Optional<User> user  = repository.findByEmail(_email);
            finalResult.add(user.get());
        }
        return ResponseEntity.ok(finalResult);
    }

    public ResponseEntity<String> acceptRequest (AcceptedPair request){
        AcceptedPair acceptedPair = new AcceptedPair();
        acceptedPair.setEmailStudent(request.getEmailStudent());
        acceptedPair.setEmailTeacher(request.getEmailTeacher());
        acceptedPairRepository.save(acceptedPair);

        List<AdvisorRequests> advisorRequests = advisorRequestRepository.findByEmailStudent(request.getEmailStudent());
        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advisorRequests1 = advisorRequests.get(i);
            advisorRequestRepository.deleteById(advisorRequests1.getId());
        }
        return ResponseEntity.ok("Success");

    }

    public ResponseEntity<String> rejectRequest (AdvisorRequests request){
        List<AdvisorRequests> advisorRequests = advisorRequestRepository.findByEmailTeacher(request.getEmailTeacher());

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advisorRequests1 = advisorRequests.get(i);
            if(advisorRequests1.getEmailStudent().equals(request.getEmailStudent())){
                advisorRequestRepository.deleteById(advisorRequests1.getId());
            }
        }
        return ResponseEntity.ok("Success");

    }
}
