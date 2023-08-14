package com.example.myworkmap.service;

import com.example.myworkmap.model.dto.AdminsDto;
import com.example.myworkmap.model.dto.request.AdminPageRequestDto;
import com.example.myworkmap.model.dto.response.AdminsResponseDto;
import com.example.myworkmap.model.projections.AdminsDtoPImpl;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminsService {
    void saveAdmin(AdminsDto adminsDto);

    List<AdminsResponseDto> findAllAdmin();

    AdminsResponseDto getAdminById(Long id);

    void deleteAdmin(Long id);

    List<AdminsDto> getAdminsByRole(String roleName);

    List<AdminsDto> getAdminsByRoleFetch(String roleName);

    List<AdminsDtoPImpl> getAdminsWithLastPassCreation();

    Page<AdminsDtoPImpl> getAdminsWithLastPassCreationPagination(AdminPageRequestDto adminPageRequestDto);
}
