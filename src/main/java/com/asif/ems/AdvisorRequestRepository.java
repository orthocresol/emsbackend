package com.asif.ems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvisorRequestRepository extends JpaRepository<AdvisorRequests, Integer> {
    List<AdvisorRequests> findByEmailStudent(String email);
}
