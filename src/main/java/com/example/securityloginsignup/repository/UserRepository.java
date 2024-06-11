package com.example.securityloginsignup.repository;

import com.example.securityloginsignup.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph("user-authorities")
    Optional<User> findUserByUsername(String username);
}
