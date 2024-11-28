package com.gomistar.proyecto_gomistar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public UserEntity save(UserEntity user) {
        return this.userRepository.save(user);
    }
}
