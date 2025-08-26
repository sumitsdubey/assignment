package com.sumit.assignment.services;

import com.sumit.assignment.dto.ModelMapper;
import com.sumit.assignment.dto.request.UserRequestDto;
import com.sumit.assignment.dto.response.UserResponseDto;
import com.sumit.assignment.entities.User;
import com.sumit.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserResponseDto createUser(UserRequestDto user) {
        if(existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        User user1 = ModelMapper.toUser(user);
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setCreatedAt(LocalDateTime.now());
        User saved = userRepository.save(user1);
        return ModelMapper.toUserResponseDto(saved);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserResponseDto updateUser(UserRequestDto user) {
        User user1 = ModelMapper.toUser(user);
        User saved = userRepository.save(user1);
        return ModelMapper.toUserResponseDto(saved);
    }

    public List<UserResponseDto> allUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(ModelMapper::toUserResponseDto).toList();
    }

}
