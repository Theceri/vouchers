package com.bezkoder.spring.jpa.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bezkoder.spring.jpa.postgresql.model.UserCred;


public interface UserRepository extends JpaRepository<UserCred, Long> {

    public UserCred findUserByUsername(String username);
}
