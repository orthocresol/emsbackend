package com.asif.ems.repository;

import com.asif.ems.entities.AdvisorRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisorRequestRepository extends JpaRepository<AdvisorRequest, Integer> {
    List<AdvisorRequest> findByEmailStudent(String email);
    List<AdvisorRequest> findByEmailTeacher(String email);

}
