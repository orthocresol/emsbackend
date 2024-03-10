package com.asif.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Teacher {
    @Id
    private UUID id;
    private String faculty;
    private String designation;
}
