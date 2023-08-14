package com.example.myworkmap.controller;

import com.example.myworkmap.model.dto.PasswordsDto;
import com.example.myworkmap.service.PasswordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("map/passwords/")
@RequiredArgsConstructor
public class PasswordsController {
    private final PasswordsService passwordsService;
    @PostMapping("save-password")
    @ResponseStatus(CREATED)
    public void savePassword(@RequestBody PasswordsDto passwordsDto) {
        passwordsService.savePassword(passwordsDto);
    }
    @DeleteMapping("delete-password/{id}")
    @ResponseStatus(OK)
    public void deletePassword(@PathVariable Long id) {
        passwordsService.deletePassword(id);
    }
    @GetMapping("find-all-password")
    @ResponseStatus(OK)
    public List<PasswordsDto> findAllPassword() {
        return passwordsService.getAllPasswords();
    }
    @GetMapping("get-password-by-id/{id}")
    @ResponseStatus(OK)
    public PasswordsDto getPasswordById(@PathVariable Long id) {
        return passwordsService.getPasswordById(id);
    }
    @PutMapping("update")
    @ResponseStatus(OK)
    public void updatePassword(){

    }
}
