package com.example.myworkmap.service;

import com.example.myworkmap.model.dto.PasswordsDto;

import java.util.List;

public interface PasswordsService {
    void savePassword(PasswordsDto passwordsDto);
    void deletePassword(Long id);
    List<PasswordsDto> getAllPasswords();
    PasswordsDto getPasswordById(Long id);
    void updatePassword(PasswordsDto passwordsDto);
}
