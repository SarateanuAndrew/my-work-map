package com.example.myworkmap.controller;

import com.example.myworkmap.model.dto.AdminsDto;
import com.example.myworkmap.model.dto.request.AdminPageRequestDto;
import com.example.myworkmap.model.dto.response.AdminsResponseDto;
import com.example.myworkmap.model.projections.AdminsDtoPImpl;
import com.example.myworkmap.service.AdminsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("map/admins/")
@RequiredArgsConstructor
public class AdminsController {
    private final AdminsService adminsService;

    @PostMapping("save-admin")
    @ResponseStatus(CREATED)
    public void saveAdmin(@RequestBody AdminsDto adminsDto) {
        adminsService.saveAdmin(adminsDto);
    }

    @DeleteMapping("delete-admin/{id}")
    @ResponseStatus(OK)
    //todo we do not remove users, we just set as disabled, the same disable password (it would be nice)
    public void deleteAdmin(@PathVariable Long id) {
        adminsService.deleteAdmin(id);
    }

    @GetMapping("find-all-admin")
    @ResponseStatus(OK)
    public List<AdminsResponseDto> findAllAdmin() {
        return adminsService.findAllAdmin();
    }

    @GetMapping("get-admin-by-id/{id}")
    @ResponseStatus(OK)
    public AdminsResponseDto getAdminById(@PathVariable Long id) {
        return adminsService.getAdminById(id);
    }

    @GetMapping("get-admin-by-role-name")
    @ResponseStatus(OK)
    public List<AdminsDto> getAdminByRoleName(@RequestParam String roleName) {
        return adminsService.getAdminsByRole(roleName);
    }

    @GetMapping("get-admin-by-role-name-fetch")
    @ResponseStatus(OK)
    public List<AdminsDto> getAdminByRoleNameFetch(@RequestParam String roleName) {
        return adminsService.getAdminsByRoleFetch(roleName);
    }

    @GetMapping("admins-last-pass-creation")
    @ResponseStatus(OK)
    public List<AdminsDtoPImpl> getAdminsWithLastPassCreation() {
        return adminsService.getAdminsWithLastPassCreation();
    }

    @GetMapping("admins-last-pass-creation-pagination")
    @ResponseStatus(OK)
    public Page<AdminsDtoPImpl> getAdminsWithLastPassCreationPagination(@RequestBody AdminPageRequestDto adminPageRequestDto) {
        return adminsService.getAdminsWithLastPassCreationPagination(adminPageRequestDto);
    }
}
