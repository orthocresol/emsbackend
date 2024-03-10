package com.asif.ems.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentProfile {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String dept;
    private String batch;
    private String studentID;
    private UUID advisorID;
    private String lock;
    private String role;
}
