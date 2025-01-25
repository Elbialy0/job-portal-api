package com.elbialy.jobportal.repository;

import com.elbialy.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String name);
}
