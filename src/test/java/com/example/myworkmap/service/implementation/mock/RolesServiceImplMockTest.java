package com.example.myworkmap.service.implementation.mock;

import com.example.myworkmap.model.dbo.RolesDbo;
import com.example.myworkmap.model.dto.RolesDto;
import com.example.myworkmap.repository.RolesRepository;
import com.example.myworkmap.service.implementation.RolesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class RolesServiceImplMockTest {
    @InjectMocks
    private RolesServiceImpl rolesService;

    @Mock
    private RolesRepository rolesRepository;

    @Test
    void saveRole() {
        RolesDto rolesDto = RolesDto.builder()
                .name("Worker")
                .description("Works")
                .createdTime(now())
                .updatedTime(now())
                .build();
        assertThatCode(() -> rolesService.saveRole(rolesDto)).doesNotThrowAnyException();
    }

    @Test
    void deleteRole() {
        assertThatCode(() -> rolesService.deleteRole(any())).doesNotThrowAnyException();
    }

    @Test
    void getAllRoles() {
        when(rolesRepository.findAll()).thenReturn(emptyList());
        assertThat(rolesService.getAllRoles()).size().isEqualTo(0);
    }

    @Test
    void getRoleById() {
        RolesDbo rolesDbo = RolesDbo.builder()
                .name("Worker")
                .description("Works")
                .createdTime(now())
                .updatedTime(now())
                .build();
        when(rolesRepository.findById(any())).thenReturn(Optional.of(rolesDbo));
        assertThat(rolesService.getRoleById(rolesDbo.getId()).getName()).isEqualTo("Worker");
    }
}