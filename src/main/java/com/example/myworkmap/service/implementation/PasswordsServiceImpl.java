package com.example.myworkmap.service.implementation;

import com.example.myworkmap.model.dbo.AdminsDbo;
import com.example.myworkmap.model.dbo.PasswordsDbo;
import com.example.myworkmap.model.dto.PasswordsDto;
import com.example.myworkmap.repository.AdminsRepository;
import com.example.myworkmap.repository.PasswordsRepository;
import com.example.myworkmap.service.PasswordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.myworkmap.model.enums.PasswordsStatusEnum.ACTIVE;

@Service
@RequiredArgsConstructor
public class PasswordsServiceImpl implements PasswordsService {
    private final PasswordsRepository passwordsRepository;
    private final AdminsRepository adminsRepository;

    @Override
    public void savePassword(PasswordsDto passwordsDto) {
        AdminsDbo adminsDbo = adminsRepository.findById(passwordsDto.getAdminsId()).orElseThrow(() -> new NoSuchElementException("Not Found User"));
        passwordsRepository.save(PasswordsDbo.builder()
                .passwordHash(passwordsDto.getPasswordHash())
                .createdTime(passwordsDto.getCreatedTime())
                .disabledAt(passwordsDto.getDisabledAt())
                .updatedTime(passwordsDto.getUpdatedTime())
                .adminsDbo(adminsDbo)
                .status(ACTIVE)
                .build());
    }

    @Override
    public void deletePassword(Long id) {
        passwordsRepository.deleteById(id);
    }

    @Override
    public List<PasswordsDto> getAllPasswords() {
        return passwordsRepository.findAllPasswords().stream()
                .map(passwordsDbo -> PasswordsDto.builder()
                        .id(passwordsDbo.getId())
                        .passwordHash(passwordsDbo.getPasswordHash())
                        .createdTime(passwordsDbo.getCreatedTime())
                        .disabledAt(passwordsDbo.getDisabledAt())
                        .updatedTime(passwordsDbo.getUpdatedTime())
                        .adminsId(passwordsDbo.getAdminsDbo().getId())
                        .status(passwordsDbo.getStatus())
                        .build())
                .toList();
    }

    @Override
    public PasswordsDto getPasswordById(Long id) {
        return passwordsRepository.findById(id)
                .map(passwordsDbo -> PasswordsDto.builder()
                        .id(passwordsDbo.getId())
                        .passwordHash(passwordsDbo.getPasswordHash())
                        .createdTime(passwordsDbo.getCreatedTime())
                        .disabledAt(passwordsDbo.getDisabledAt())
                        .updatedTime(passwordsDbo.getUpdatedTime())
                        .status(passwordsDbo.getStatus())
                        .build())
                .orElseThrow();
    }

    @Override
    public void updatePassword(PasswordsDto passwordsDto){
        PasswordsDbo passwordsDbo = passwordsRepository.findById(passwordsDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Password don't exist"));
        AdminsDbo adminsDbo = adminsRepository.findById(passwordsDto.getAdminsId())
                .orElseThrow(() -> new NoSuchElementException("Admin not found"));
        passwordsDbo.setPasswordHash(passwordsDto.getPasswordHash());
        passwordsDbo.setAdminsDbo(adminsDbo);
        passwordsDbo.setStatus(passwordsDto.getStatus());
        passwordsDbo.setUpdatedTime(LocalDateTime.now());
        passwordsDbo.setDisabledAt(passwordsDto.getDisabledAt());
        passwordsDbo.setCreatedTime(passwordsDto.getCreatedTime());
        passwordsRepository.save(passwordsDbo);
    }
}
