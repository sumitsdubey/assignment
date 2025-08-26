package com.sumit.assignment.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
