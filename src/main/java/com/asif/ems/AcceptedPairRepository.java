package com.asif.ems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptedPairRepository extends JpaRepository<AcceptedPair, Integer> {
    AcceptedPair findByEmailStudent(String email);
    List<AcceptedPair> findByEmailTeacher(String email);

}
