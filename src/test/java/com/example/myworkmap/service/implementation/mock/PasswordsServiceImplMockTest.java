package com.example.myworkmap.service.implementation.mock;

import com.example.myworkmap.model.dbo.AdminsDbo;
import com.example.myworkmap.model.dbo.PasswordsDbo;
import com.example.myworkmap.model.dto.PasswordsDto;
import com.example.myworkmap.repository.AdminsRepository;
import com.example.myworkmap.repository.PasswordsRepository;
import com.example.myworkmap.service.implementation.PasswordsServiceImpl;
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
class PasswordsServiceImplMockTest {

    @InjectMocks
    private PasswordsServiceImpl passwordsService;

    @Mock
    private PasswordsRepository passwordsRepository;

    @Mock
    private AdminsRepository adminsRepository;

    @Test
    void savePassword() {
        AdminsDbo adminsDbo = AdminsDbo.builder().id(1L).build();
        when(adminsRepository.findById(any())).thenReturn(Optional.of(adminsDbo));
        PasswordsDto passwordsDto = PasswordsDto.builder()
                .passwordHash("fdfsdf")
                .createdTime(now())
                .updatedTime(now())
                .disabledAt(now())
                .build();
        assertThatCode(() -> passwordsService.savePassword(passwordsDto)).doesNotThrowAnyException();
    }

    @Test
    void deletePassword() {
        assertThatCode(() -> passwordsService.deletePassword(any())).doesNotThrowAnyException();
    }

    @Test
    void getAllPasswords() {
        when(passwordsService.getAllPasswords()).thenReturn(emptyList());
        assertThat(passwordsService.getAllPasswords()).isEmpty();
    }

    @Test
    void getPasswordById() {
        AdminsDbo adminsDbo = AdminsDbo.builder()
                .id(1L)
                .build();
        when(adminsRepository.findById(any())).thenReturn(Optional.of(adminsDbo));
        PasswordsDbo passwordsDbo = PasswordsDbo.builder()
                .passwordHash("hd")
                .id(2L)
                .build();
        when(passwordsRepository.findById(any())).thenReturn(Optional.of(passwordsDbo));
        assertThat(passwordsService.getPasswordById(passwordsDbo.getId()).getPasswordHash()).isEqualTo("hd");
    }
}