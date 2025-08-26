package com.sumit.assignment.controller;

import com.sumit.assignment.dto.ModelMapper;
import com.sumit.assignment.dto.request.UserRequestDto;
import com.sumit.assignment.dto.response.UserResponseDto;
import com.sumit.assignment.entities.User;
import com.sumit.assignment.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserServices userServices;




    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Validated @RequestBody UserRequestDto user) {
        UserResponseDto updated = userServices.updateUser(user);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userServices.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ModelMapper.toUserResponseDto(user), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userServices.allUsers(), HttpStatus.OK);
    }


}
