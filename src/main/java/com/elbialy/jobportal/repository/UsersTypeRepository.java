package com.elbialy.jobportal.repository;

import com.elbialy.jobportal.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersTypeRepository extends JpaRepository<UserType,Integer> {
    UserType findByUserTypeName(String userTypeName);
}
