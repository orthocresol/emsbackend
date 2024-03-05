package com.asif.ems.dto;

import lombok.Data;

@Data
public class StudentProfile {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String dept;
    private String batch;
    private String studentID;
    private Integer advisorID;
    private String lock;
    private String role;
}
