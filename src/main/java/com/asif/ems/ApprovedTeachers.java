package com.asif.ems;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ApprovedTeachers {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String faculty;
    private String designation;
}
