package com.asif.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Teacher {
    @Id
    private Integer id;
    private String faculty;
    private String designation;
}
