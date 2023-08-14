package com.example.myworkmap.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminsResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String roleName;
    private int level;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastActivityTime;
    private String status;
    private List<String> passwords;
}
