package com.example.SecurityG129.service;

import com.example.SecurityG129.model.Permission;
import com.example.SecurityG129.model.User;
import com.example.SecurityG129.repository.PermissionRepository;
import com.example.SecurityG129.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User)authentication.getPrincipal();
    }

    public void changeUser(String fullName, String email,  String oldPassword, String newPassword, String reNewPassword) {

        if(fullName!=null){
            getCurrentUser().setFullName(fullName);
        }

        User user = userRepository.getUserByEmail(email);

        if(user!=null){
            return;
        }

        if(email!=null){
            getCurrentUser().setEmail(email);
        }

        if(!encoder.matches(oldPassword, getCurrentUser().getPassword())){
            return;
        }



        if(!newPassword.equals(reNewPassword)){
            return;
        }

        getCurrentUser().setPassword(encoder.encode(newPassword));

        userRepository.save(getCurrentUser());

    }
}
