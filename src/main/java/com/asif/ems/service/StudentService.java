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
public class StudentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdvisorRequestRepository advisorRequestRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public ResponseEntity<List<StudentProfile>> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentProfile> studentProfiles = new ArrayList<>();

        for(int i = 0; i < students.size(); i++){
            Student student = students.get(i);
            Optional<User> _user = userRepository.findById(student.getId());
            User user = _user.get();

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

            studentProfiles.add(studentProfile);
        }

        return ResponseEntity.ok(studentProfiles);

    }

    public ResponseEntity<StudentProfile> getInfo (UUID id){
        Optional<User> _user = userRepository.findById(id);
        User user = _user.get();

        StudentProfile studentProfile = new StudentProfile();
        Optional<Student> _student = studentRepository.findById(user.getId());
        Student student = _student.get();

        studentProfile.setId(user.getId());
        studentProfile.setDept(student.getDept());
        studentProfile.setName(user.getName());
        studentProfile.setPhone(user.getPhone());
        studentProfile.setStudentID(student.getStudentID());
        studentProfile.setBatch(student.getBatch());
        studentProfile.setEmail(user.getEmail());
        studentProfile.setAdvisorID(student.getAdvisorID());
        studentProfile.setLock(user.getLock());
        studentProfile.setRole(String.valueOf(user.getRole()));

        return ResponseEntity.ok(studentProfile);
    }

    public ResponseEntity<String> update(UUID id, StudentProfile profile){
        Optional<User> _user = userRepository.findById(id);

        User user = _user.get();
        Optional<Student> _student = studentRepository.findById(user.getId());
        Student student = _student.get();

        user.setName(profile.getName());
        user.setPhone(profile.getPhone());

        student.setDept(profile.getDept());
        student.setStudentID(profile.getStudentID());
        student.setBatch(profile.getBatch());
        userRepository.save(user);
        studentRepository.save(student);

        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<TeacherProfile> findAdvisor(UUID id){
        Optional<User> _user = userRepository.findById(id);
        User user = _user.get();
        Optional<Student> _student = studentRepository.findById(user.getId());
        Student student = _student.get();

        Optional<Teacher> _teacher = teacherRepository.findById(student.getAdvisorID());
        Teacher teacher = _teacher.get();

        Optional<User> _userTeacher = userRepository.findById(teacher.getId());
        User userTeacher = _userTeacher.get();


        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setName(userTeacher.getName());
        teacherProfile.setFaculty(teacher.getFaculty());
        teacherProfile.setDesignation(teacher.getDesignation());

        return ResponseEntity.ok(teacherProfile);
    }

    public ResponseEntity<List<TeacherProfile>> getAllSendRequestList(UUID id) {
        Optional<User> __user = userRepository.findById(id);
        User user_ = __user.get();
        List<AdvisorRequest> advisorRequests = advisorRequestRepository.findByEmailStudent(user_.getEmail());
        List<TeacherProfile> finalResult = new ArrayList<>();

        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequest advs  = advisorRequests.get(i);
            String _email = advs.getEmailTeacher();
            Optional<User> _user  = userRepository.findByEmail(_email);
            User user = _user.get();
            Optional<Teacher> _teacher = teacherRepository.findById(user.getId());
            Teacher teacher = _teacher.get();


            TeacherProfile teacherProfile = new TeacherProfile();

            teacherProfile.setId(user.getId());
            teacherProfile.setFaculty(teacher.getFaculty());
            teacherProfile.setName(user.getName());
            teacherProfile.setPhone(user.getPhone());
            teacherProfile.setDesignation(teacher.getDesignation());
            teacherProfile.setEmail(user.getEmail());
            teacherProfile.setLock(user.getLock());

            finalResult.add(teacherProfile);
        }
        return ResponseEntity.ok(finalResult);
    }

    public ResponseEntity<String> addRequest (AdvisorRequest request){
        AdvisorRequest advisorRequest = new AdvisorRequest();
        advisorRequest.setEmailStudent(request.getEmailStudent());
        advisorRequest.setEmailTeacher(request.getEmailTeacher());
        advisorRequestRepository.save(advisorRequest);
        return ResponseEntity.ok("Success");

    }

    public ResponseEntity<List<TeacherProfile>> getAvailableTeachers(UUID id){

        Optional<User> _userStudent = userRepository.findById(id);
        User userStudent = _userStudent.get();

        Optional<Student> _student = studentRepository.findById(userStudent.getId());
        Student student = _student.get();

        List<AdvisorRequest> advisorRequests = advisorRequestRepository.findByEmailStudent(userStudent.getEmail());
        List<User> requestedTeachers = new ArrayList<>();
        for(int i = 0; i < advisorRequests.size(); i++){
            AdvisorRequest advisorRequest = advisorRequests.get(i);

            Optional<User> _user = userRepository.findByEmail(advisorRequest.getEmailTeacher());
            User user = _user.get();

            requestedTeachers.add(user);
        }

        List<Teacher> teachers = teacherRepository.findAll();

        List<Teacher> availableTeachers = new ArrayList<>();

        for(int i = 0; i < teachers.size(); i++){
            Teacher teacher = teachers.get(i);
            boolean isExist = false;
            for(int j = 0; j < requestedTeachers.size(); j++){
                User requestedTeacher = requestedTeachers.get(j);
                if(requestedTeacher.getId().equals(teacher.getId())){
                    isExist = true;
                }
            }
            if(teacher.getId().equals(student.getAdvisorID())) continue;
            if(!isExist) {
                availableTeachers.add(teacher);
            }
        }
        List<TeacherProfile> teacherProfiles = new ArrayList<>();
        for(int i = 0; i < availableTeachers.size(); i++) {
            Teacher teacher = availableTeachers.get(i);

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
}
