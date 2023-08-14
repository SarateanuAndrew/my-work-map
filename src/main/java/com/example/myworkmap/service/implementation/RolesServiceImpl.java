package com.example.myworkmap.service.implementation;

import com.example.myworkmap.model.dbo.RolesDbo;
import com.example.myworkmap.model.dto.RolesDto;
import com.example.myworkmap.repository.RolesRepository;
import com.example.myworkmap.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    @Override
    public void saveRole(RolesDto rolesDto) {
        rolesRepository.save(RolesDbo.builder()
                .createdTime(rolesDto.getCreatedTime())
                .description(rolesDto.getDescription())
                .updatedTime(rolesDto.getUpdatedTime())
                .name(rolesDto.getName())
                .build());
    }

    @Override
    public void deleteRole(Long id) {
        rolesRepository.deleteById(id);
    }

    @Override
    public List<RolesDto> getAllRoles() {
        return rolesRepository.findAll().stream()
                .map(rolesDbo -> RolesDto.builder()
                        .id(rolesDbo.getId())
                        .createdTime(rolesDbo.getCreatedTime())
                        .description(rolesDbo.getDescription())
                        .updatedTime(rolesDbo.getUpdatedTime())
                        .name(rolesDbo.getName())
                        .build())
                .toList();
    }

    @Override
    public RolesDto getRoleById(Long id) {
        return rolesRepository.findById(id).map(rolesDbo -> RolesDto.builder()
                        .id(rolesDbo.getId())
                        .createdTime(rolesDbo.getCreatedTime())
                        .description(rolesDbo.getDescription())
                        .updatedTime(rolesDbo.getUpdatedTime())
                        .name(rolesDbo.getName())
                        .build())
                .orElseThrow();

    }

    @Override
    public void updateRole(RolesDto rolesDto) {
        RolesDbo rolesDbo = rolesRepository.findById(rolesDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Role doesn't exist!!!"));
        rolesDbo.setName(rolesDto.getName());
        rolesDbo.setDescription(rolesDto.getDescription());
        rolesDbo.setUpdatedTime(rolesDto.getUpdatedTime());
        rolesRepository.save(rolesDbo);
    }

    @Override
    public void updateRoleQuery(RolesDto rolesDto) {
        rolesRepository.updateRole(rolesDto);
    }

    @Override
    public Map<DayOfWeek, List<Integer>> groupDaysOfBirthday(LocalDate localDate) {
        return Stream.iterate(localDate, localDate1 -> localDate1.plusYears(1))
                .limit(ChronoUnit.YEARS.between(localDate, LocalDate.now()) + 1)
                .collect(Collectors.groupingBy(LocalDate::getDayOfWeek,
                        Collectors.mapping(LocalDate::getYear, Collectors.toList())));
    }
}
