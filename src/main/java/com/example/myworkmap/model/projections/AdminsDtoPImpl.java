package com.example.myworkmap.model.projections;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminsDtoPImpl {
    private Integer adminId;

    private String firstName;

    private String lastName;

    private String email;

    private String getRoleName;

    private LocalDateTime passwordCreatedTime;
}
