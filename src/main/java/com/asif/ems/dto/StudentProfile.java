package com.asif.ems.dto;

import lombok.Data;

@Data
public class StudentProfile {
    private String name;
    private String phone;
    private String email;
    private String dept;
    private String batch;
    private String studentID;
}