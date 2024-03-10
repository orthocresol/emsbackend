package com.asif.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Student {
    @Id
    private UUID id;
    private String dept;
    private String studentID;
    private String batch;
    private UUID advisorID;
}
