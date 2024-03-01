package com.asif.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AcceptedPair {
    @Id
    @GeneratedValue
    private Integer id;
    private String emailStudent;
    private String emailTeacher;
}
