package com.asif.ems.controller;

import com.asif.ems.dto.StudentProfile;
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
public class StudentService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private AcceptedPairRepository acceptedPairRepository;
    @Autowired
    private AdvisorRequestRepository advisorRequestRepository;

    public ResponseEntity<List<User>> getAllStudents(){
        List<User> users = repository.findByRole(Role.STUDENT);
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getInfo (String email){
        Optional<User> user = repository.findByEmail(email);
        User nonNullUser = user.get();
        return ResponseEntity.ok(nonNullUser);
    }

    public ResponseEntity<String> update(StudentProfile profile){
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

    public ResponseEntity<User> findAdvisor(String email){
        AcceptedPair acceptedPair = acceptedPairRepository.findByEmailStudent(email);
        Optional<User> user = repository.findByEmail(acceptedPair.getEmailTeacher());
        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<List<User>> getAllSendRequestList(String email) {
        List<AdvisorRequests> advisorRequests = advisorRequestRepository.findByEmailStudent(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailTeacher();
            Optional<User> user  = repository.findByEmail(_email);
            finalResult.add(user.get());
        }
        return ResponseEntity.ok(finalResult);
    }

    public ResponseEntity<String> addRequest (AdvisorRequests request){
        AdvisorRequests advisorRequest = new AdvisorRequests();
        advisorRequest.setEmailStudent(request.getEmailStudent());
        advisorRequest.setEmailTeacher(request.getEmailTeacher());
        advisorRequestRepository.save(advisorRequest);
        return ResponseEntity.ok("Success");

    }

    public ResponseEntity<List<User>> getAvailableTeachers(String email){
        List<AdvisorRequests> advisorRequests = advisorRequestRepository.findByEmailStudent(email);
        List<User> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequests advs  = advisorRequests.get(i);
            String _email = advs.getEmailTeacher();
            Optional<User> user  = repository.findByEmail(_email);
            finalResult.add(user.get());
        }

        List<User> listAll = repository.findAll();

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
                if(user.getRole().equals(Role.TEACHER)){
                    finale.add(user);
                }

            }
        }
        return ResponseEntity.ok(finale);
    }
}
