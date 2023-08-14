package com.example.myworkmap.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminsDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long rolesId;
    private int level;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastActivityTime;
    private String status;
    private String password;
}