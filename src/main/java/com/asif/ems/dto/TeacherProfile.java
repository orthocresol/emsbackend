package com.asif.ems.dto;

import lombok.Data;

@Data
public class TeacherProfile {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String faculty;
    private String designation;
    private String lock;
    private String role;
}
