package com.example.myworkmap.model.projections;

import java.time.LocalDateTime;


public interface AdminsDtoP {
    Integer getAdminId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getRoleName();

    LocalDateTime getCreatedTime();
}
