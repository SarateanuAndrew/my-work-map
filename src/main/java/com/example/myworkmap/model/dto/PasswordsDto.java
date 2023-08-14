package com.example.myworkmap.model.dto;

import com.example.myworkmap.model.enums.PasswordsStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PasswordsDto {
    private Long id;
    private String passwordHash;
    private PasswordsStatusEnum status;
    private Long adminsId;
    private LocalDateTime disabledAt;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
