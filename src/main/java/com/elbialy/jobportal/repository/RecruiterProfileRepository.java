package com.elbialy.jobportal.repository;

import com.elbialy.jobportal.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {
}
