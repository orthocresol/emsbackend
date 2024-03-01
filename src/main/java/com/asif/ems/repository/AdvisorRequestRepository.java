package com.asif.ems.repository;

import com.asif.ems.entities.AdvisorRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisorRequestRepository extends JpaRepository<AdvisorRequests, Integer> {
    List<AdvisorRequests> findByEmailStudent(String email);
    List<AdvisorRequests> findByEmailTeacher(String email);

}
