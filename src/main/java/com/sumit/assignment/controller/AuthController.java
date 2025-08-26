package com.sumit.assignment.controller;


import com.sumit.assignment.dto.request.LoginRequestDto;
import com.sumit.assignment.dto.request.UserRequestDto;
import com.sumit.assignment.dto.response.UserResponseDto;
import com.sumit.assignment.jwt.JwtUtil;
import com.sumit.assignment.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDto user) {
        UserResponseDto created = userServices.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto user) {
        Authentication auth = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth != null && auth.isAuthenticated()) {
            String jwt = jwtUtil.generateJwtToken(auth);

            return new ResponseEntity<>(Map.of("token", jwt), HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
}
