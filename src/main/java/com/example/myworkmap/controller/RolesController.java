package com.example.myworkmap.controller;

import com.example.myworkmap.model.dto.RolesDto;
import com.example.myworkmap.service.RolesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/map/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RolesService rolesService;

    @PostMapping("save-role")
    @ResponseStatus(CREATED)
    public void saveRole(@Valid @RequestBody RolesDto rolesDto) {
        rolesService.saveRole(rolesDto);
    }

    @DeleteMapping("delete-role/{id}")
    @ResponseStatus(OK)
    public void deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
    }

    @GetMapping("find-all-role")
    @ResponseStatus(OK)
    public List<RolesDto> findAllRole() {
        return rolesService.getAllRoles();
    }

    @GetMapping("get-role-by-id/{id}")
    @ResponseStatus(OK)
    public RolesDto getRoleById(@PathVariable Long id) {
        return rolesService.getRoleById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(OK)
    public void updateRole(@RequestBody RolesDto rolesDto){
        rolesService.updateRole(rolesDto);
    }

    @PutMapping("/update-with-query")
    @ResponseStatus(OK)
    public void updateRoleQuery(@RequestBody RolesDto rolesDto){
        rolesService.updateRoleQuery(rolesDto);
    }

}
