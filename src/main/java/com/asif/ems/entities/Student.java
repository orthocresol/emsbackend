package com.asif.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {
    @Id
    private Integer id;
    private String dept;
    private String studentID;
    private String batch;
    private Integer advisorID;
}
