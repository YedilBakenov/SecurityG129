package com.example.SecurityG129.repository;

import com.example.SecurityG129.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);

}
