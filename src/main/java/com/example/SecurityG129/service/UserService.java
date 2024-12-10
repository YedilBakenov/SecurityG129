package com.example.SecurityG129.service;

import com.example.SecurityG129.model.User;
import com.example.SecurityG129.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String name){
        return userRepository.getUserByEmail(name);
    }
}
