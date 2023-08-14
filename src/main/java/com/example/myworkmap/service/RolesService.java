package com.example.myworkmap.service;

import com.example.myworkmap.model.dto.RolesDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RolesService {
    void saveRole(RolesDto rolesDto);
    void deleteRole(Long id);
    List<RolesDto> getAllRoles();
    RolesDto getRoleById(Long id);
    void updateRole(RolesDto rolesDto);
    void updateRoleQuery(RolesDto rolesDto);
    Map<DayOfWeek, List<Integer>> groupDaysOfBirthday(LocalDate localDate);
}
