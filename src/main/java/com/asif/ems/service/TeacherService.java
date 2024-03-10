package com.asif.ems.service;

import com.asif.ems.dto.StudentProfile;
import com.asif.ems.dto.TeacherProfile;
import com.asif.ems.entities.*;
import com.asif.ems.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AcceptedPairRepository acceptedPairRepository;
    @Autowired
    private AdvisorRequestRepository advisorRequestRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity<List<TeacherProfile>> getAllTeachers(){
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherProfile> teacherProfiles = new ArrayList<>();

        for(int i = 0; i < teachers.size(); i++){
            Teacher teacher = teachers.get(i);
            Optional<User> _user = userRepository.findById(teacher.getId());
            User user = _user.get();

            TeacherProfile teacherProfile = new TeacherProfile();
            teacherProfile.setId(teacher.getId());
            teacherProfile.setName(user.getName());
            teacherProfile.setFaculty(teacher.getFaculty());
            teacherProfile.setPhone(user.getPhone());
            teacherProfile.setEmail(user.getEmail());
            teacherProfile.setDesignation(teacher.getDesignation());
            teacherProfile.setLock(user.getLock());
            teacherProfile.setRole(String.valueOf(user.getRole()));

            teacherProfiles.add(teacherProfile);
        }

        return ResponseEntity.ok(teacherProfiles);
    }

    public ResponseEntity<TeacherProfile> getInfo (UUID id){
        Optional<User> _user = userRepository.findById(id);
        User user = _user.get();

        TeacherProfile teacherProfile = new TeacherProfile();
        Optional<Teacher> _teacher = teacherRepository.findById(user.getId());
        Teacher teacher = _teacher.get();

        teacherProfile.setId(user.getId());
        teacherProfile.setFaculty(teacher.getFaculty());
        teacherProfile.setName(user.getName());
        teacherProfile.setPhone(user.getPhone());
        teacherProfile.setDesignation(teacher.getDesignation());
        teacherProfile.setEmail(user.getEmail());
        teacherProfile.setRole(String.valueOf(user.getRole()));


        return ResponseEntity.ok(teacherProfile);
    }

    public ResponseEntity<String> update(UUID id, TeacherProfile profile){
        Optional<User> _user = userRepository.findById(id);

        User user = _user.get();
        Optional<Teacher> _teacher = teacherRepository.findById(user.getId());
        Teacher teacher = _teacher.get();

        user.setName(profile.getName());
        user.setPhone(profile.getPhone());

        teacher.setFaculty(profile.getFaculty());
        teacher.setDesignation(profile.getDesignation());
        userRepository.save(user);
        teacherRepository.save(teacher);

        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<List<StudentProfile>> getEnrolledStudents(String email){
        List<AcceptedPair> acceptedPairs = acceptedPairRepository.findByEmailTeacher(email);
        List<StudentProfile> result = new ArrayList<>();
        for(int i = 0; i < acceptedPairs.size(); i++){
            AcceptedPair acceptedPair = acceptedPairs.get(i);
            Optional<User> _user = userRepository.findByEmail(acceptedPair.getEmailStudent());
            User user = _user.get();

            Optional<Student> _student = studentRepository.findById(user.getId());
            Student student = _student.get();

            StudentProfile studentProfile = new StudentProfile();

            studentProfile.setId(student.getId());
            studentProfile.setName(user.getName());
            studentProfile.setDept(student.getDept());
            studentProfile.setPhone(user.getPhone());
            studentProfile.setEmail(user.getEmail());
            studentProfile.setStudentID(student.getStudentID());
            studentProfile.setBatch(student.getBatch());
            studentProfile.setAdvisorID(student.getAdvisorID());
            studentProfile.setLock(user.getLock());


            result.add(studentProfile);
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<StudentProfile>> getRequestedStudents(UUID id) {
        Optional<User> __user = userRepository.findById(id);
        User user_ = __user.get();
        List<AdvisorRequest> advisorRequests = advisorRequestRepository.findByEmailTeacher(user_.getEmail());
        List<StudentProfile> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequest advs  = advisorRequests.get(i);
            String _email = advs.getEmailStudent();
            Optional<User> _user  = userRepository.findByEmail(_email);
            User user = _user.get();

            Optional<Student> _student = studentRepository.findById(user.getId());
            Student student = _student.get();

            StudentProfile studentProfile = new StudentProfile();

            studentProfile.setId(student.getId());
            studentProfile.setName(user.getName());
            studentProfile.setDept(student.getDept());
            studentProfile.setPhone(user.getPhone());
            studentProfile.setEmail(user.getEmail());
            studentProfile.setStudentID(student.getStudentID());
            studentProfile.setBatch(student.getBatch());
            studentProfile.setAdvisorID(student.getAdvisorID());
            studentProfile.setLock(user.getLock());


            finalResult.add(studentProfile);
        }
        return ResponseEntity.ok(finalResult);
    }

    public ResponseEntity<String> acceptRequest (AcceptedPair request){
        AcceptedPair acceptedPair = new AcceptedPair();
        acceptedPair.setEmailStudent(request.getEmailStudent());
        acceptedPair.setEmailTeacher(request.getEmailTeacher());
        acceptedPairRepository.save(acceptedPair);

        Optional<User> _user = userRepository.findByEmail(request.getEmailStudent());
        User user = _user.get();

        Optional<User> _userTeacher = userRepository.findByEmail(request.getEmailTeacher());
        User userTeacher = _userTeacher.get();

        Optional<Student> _student = studentRepository.findById(user.getId());
        Student student = _student.get();

        student.setAdvisorID(userTeacher.getId());
        studentRepository.save(student);


        List<AdvisorRequest> advisorRequests = advisorRequestRepository.findByEmailStudent(request.getEmailStudent());
        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequest advisorRequest1 = advisorRequests.get(i);
            advisorRequestRepository.deleteById(advisorRequest1.getId());
        }
        return ResponseEntity.ok("Success");

    }

    public ResponseEntity<String> rejectRequest (AdvisorRequest request){
        List<AdvisorRequest> advisorRequests = advisorRequestRepository.findByEmailTeacher(request.getEmailTeacher());

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequest advisorRequest1 = advisorRequests.get(i);
            if(advisorRequest1.getEmailStudent().equals(request.getEmailStudent())){
                advisorRequestRepository.deleteById(advisorRequest1.getId());
            }
        }
        return ResponseEntity.ok("Success");

    }
}
