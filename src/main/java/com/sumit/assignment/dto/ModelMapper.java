package com.sumit.assignment.dto;

import com.sumit.assignment.dto.request.NoteRequestDto;
import com.sumit.assignment.dto.request.UserRequestDto;
import com.sumit.assignment.dto.response.UserResponseDto;
import com.sumit.assignment.entities.Note;
import com.sumit.assignment.entities.User;

import java.time.LocalDateTime;

public class ModelMapper {


    public static User toUser(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static Note toNote(NoteRequestDto noteRequestDto) {
        return Note.builder()
                .title(noteRequestDto.getTitle())
                .content(noteRequestDto.getContent())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
