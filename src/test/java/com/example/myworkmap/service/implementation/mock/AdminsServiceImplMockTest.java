package com.example.myworkmap.service.implementation.mock;

import com.example.myworkmap.model.dbo.AdminsDbo;
import com.example.myworkmap.model.dbo.RolesDbo;
import com.example.myworkmap.model.dto.AdminsDto;
import com.example.myworkmap.repository.AdminsRepository;
import com.example.myworkmap.repository.PasswordsRepository;
import com.example.myworkmap.repository.RolesRepository;
import com.example.myworkmap.service.implementation.AdminsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdminsServiceImplMockTest {
    @InjectMocks
    private AdminsServiceImpl adminsService;

    @Mock
    private AdminsRepository adminsRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordsRepository passwordsRepository;

    @Test
    void saveAdmin() {
        RolesDbo rolesDbo = RolesDbo.builder()
                .id(1L)
                .build();
        when(rolesRepository.findById(any())).thenReturn(Optional.of(rolesDbo));
        AdminsDto adminsDto = AdminsDto.builder()
                .id(2L)
                .rolesId(rolesDbo.getId())
                .build();
        assertThatCode(() -> adminsService.saveAdmin(adminsDto)).doesNotThrowAnyException();
    }

    @Test
    void findAllAdmin() {
        when(adminsRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(adminsService.findAllAdmin()).isEmpty();
    }

    @Test
    void getAdminById() {
        AdminsDbo adminsDbo = AdminsDbo.builder()
                .firstName("a")
                .build();
        when(adminsRepository.findById(any())).thenReturn(Optional.of(adminsDbo));
        assertThat(adminsService.getAdminById(adminsDbo.getId()).getFirstName()).isEqualTo("a");
    }

    @Test
    void deleteAdmin() {
        AdminsDto adminsDto = AdminsDto.builder().build();
        assertThatCode(() -> adminsService.deleteAdmin(adminsDto.getId())).doesNotThrowAnyException();
    }
}