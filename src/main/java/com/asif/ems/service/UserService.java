package com.asif.ems.service;

import com.asif.ems.entities.Role;
import com.asif.ems.entities.Student;
import com.asif.ems.entities.Teacher;
import com.asif.ems.entities.User;
import com.asif.ems.repository.StudentRepository;
import com.asif.ems.repository.TeacherRepository;
import com.asif.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;


    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findByRole(Role.USER);
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getAUser(String email){
        Optional<User> _user = userRepository.findByEmail(email);
        User user = _user.get();
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<String> updateUser(Integer id, HashMap<String, String> action){
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
        Optional<User> user = userRepository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("LOCK");
        userRepository.save(nonNulluser);
    }

    public void unlockUser(Integer id){
        Optional<User> user = userRepository.findById(id);
        User nonNulluser = user.get();
        nonNulluser.setLock("UNLOCK");
        userRepository.save(nonNulluser);
    }

    public void updateRolesToTeacher(Integer id){
        Optional<User> user = userRepository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.TEACHER);
        userRepository.save(nonNullUser);

        Teacher teacher = new Teacher();
        teacher.setId(nonNullUser.getId());
        teacher.setFaculty("No faculty assigned");
        teacher.setDesignation("No designation assigned");
        teacherRepository.save(teacher);
    }

    public void updateRolesToStudent(Integer id){
        Optional<User> user = userRepository.findById(id);
        User nonNullUser = user.get();
        nonNullUser.setRole(Role.STUDENT);
        userRepository.save(nonNullUser);

        Student student = new Student();
        student.setId(nonNullUser.getId());
        student.setDept("No dept assigned");
        student.setStudentID("No student id assigned");
        student.setBatch("No batch assigned");
        student.setAdvisorID(-1);
        studentRepository.save(student);
    }
}
