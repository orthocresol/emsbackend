package com.asif.ems;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AdvisorRequests {
    @Id
    @GeneratedValue
    private Integer id;
    private String emailTeacher;
    private String emailStudent;

}
