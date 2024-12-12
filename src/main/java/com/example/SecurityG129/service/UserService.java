package com.example.SecurityG129.service;

import com.example.SecurityG129.model.Permission;
import com.example.SecurityG129.model.User;
import com.example.SecurityG129.repository.PermissionRepository;
import com.example.SecurityG129.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User getUserByEmail(String name){
        return userRepository.getUserByEmail(name);
    }
    public void addUser(User user, String rePassword){
        User user1 = userRepository.getUserByEmail(user.getEmail());

        if(user1!=null){
            return;
        }
        if(!user.getPassword().equals(rePassword)){
            return;
        }
        user.setPassword(encoder.encode(rePassword));

        Permission permission = permissionRepository.getStandartPermission();

        user.setPermissions(List.of(permission));

        System.out.println(permission);

        userRepository.save(user);

    }

}
