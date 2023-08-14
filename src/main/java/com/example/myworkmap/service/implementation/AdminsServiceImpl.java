package com.example.myworkmap.service.implementation;

import com.example.myworkmap.model.dbo.AdminsDbo;
import com.example.myworkmap.model.dbo.PasswordsDbo;
import com.example.myworkmap.model.dto.AdminsDto;
import com.example.myworkmap.model.dto.request.AdminPageRequestDto;
import com.example.myworkmap.model.dto.request.SortRequestDto;
import com.example.myworkmap.model.dto.response.AdminsResponseDto;
import com.example.myworkmap.model.projections.AdminsDtoPImpl;
import com.example.myworkmap.repository.AdminsRepository;
import com.example.myworkmap.repository.RolesRepository;
import com.example.myworkmap.service.AdminsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.myworkmap.model.enums.PasswordsStatusEnum.ACTIVE;

@Service
@RequiredArgsConstructor
public class AdminsServiceImpl implements AdminsService {
    private final AdminsRepository adminsRepository;
    private final RolesRepository rolesRepository;

    @Override
    public void saveAdmin(AdminsDto adminsDto) {
        AdminsDbo adminsDbo = AdminsDbo.builder()
                .level(adminsDto.getLevel())
                .createdTime(adminsDto.getCreatedTime())
                .status(adminsDto.getStatus())
                .updatedTime(adminsDto.getUpdatedTime())
                .email(adminsDto.getEmail())
                .firstName(adminsDto.getFirstName())
                .lastActivityTime(adminsDto.getLastActivityTime())
                //todo extract to variable
                .role(rolesRepository.findById(adminsDto.getRolesId()).orElseThrow())
                .lastName(adminsDto.getLastName())
                .build();

        //todo remove password. We need only empty admin without pass and send mocked link
        PasswordsDbo passwordsDbo = PasswordsDbo.builder()
                .passwordHash(adminsDto.getPassword())
                .status(ACTIVE)
                .adminsDbo(adminsDbo)
                .build();
        adminsDbo.setPasswordsDbos(List.of(passwordsDbo));
        adminsRepository.save(adminsDbo);
    }

    @Override
    public List<AdminsResponseDto> findAllAdmin() {
        return adminsRepository.findAllAdmins().stream()
                .map(adminsDbo -> AdminsResponseDto.builder()
                        .id(adminsDbo.getId())
                        .level(adminsDbo.getLevel())
                        .createdTime(adminsDbo.getCreatedTime())
                        .status(adminsDbo.getStatus())
                        .updatedTime(adminsDbo.getUpdatedTime())
                        .email(adminsDbo.getEmail())
                        .firstName(adminsDbo.getFirstName())
                        .lastActivityTime(adminsDbo.getLastActivityTime())
                        .lastName(adminsDbo.getLastName())
                        .roleName(adminsDbo.getRole().getName())
                        .passwords(adminsDbo.getPasswordsDbos().stream()
                                .map(PasswordsDbo::getPasswordHash)
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public AdminsResponseDto getAdminById(Long id) {
        return adminsRepository.findAdminById(id)
                .map(adminsDbo -> AdminsResponseDto.builder()
                        .id(adminsDbo.getId())
                        .level(adminsDbo.getLevel())
                        .createdTime(adminsDbo.getCreatedTime())
                        .status(adminsDbo.getStatus())
                        .updatedTime(adminsDbo.getUpdatedTime())
                        .email(adminsDbo.getEmail())
                        .firstName(adminsDbo.getFirstName())
                        .lastActivityTime(adminsDbo.getLastActivityTime())
                        .lastName(adminsDbo.getLastName())
                        .passwords(adminsDbo.getPasswordsDbos().stream()
                                .map(PasswordsDbo::getPasswordHash)
                                .toList())
                        .roleName(adminsDbo.getRole().getName())
                        .build())
                .orElseThrow();
    }

    @Override
    public void deleteAdmin(Long id) {
        adminsRepository.deleteById(id);
    }

    @Override
    public List<AdminsDto> getAdminsByRole(String roleName) {
        return adminsRepository.getAdminsByRoleName(roleName).stream()
                .map(adminsDbo -> AdminsDto.builder()
                        .id(adminsDbo.getId())
                        .level(adminsDbo.getLevel())
                        .createdTime(adminsDbo.getCreatedTime())
                        .status(adminsDbo.getStatus())
                        .updatedTime(adminsDbo.getUpdatedTime())
                        .email(adminsDbo.getEmail())
                        .firstName(adminsDbo.getFirstName())
                        .lastActivityTime(adminsDbo.getLastActivityTime())
                        .lastName(adminsDbo.getLastName())
                        .rolesId(adminsDbo.getRole().getId())
                        .build())
                .toList();
    }

    @Override
    public List<AdminsDto> getAdminsByRoleFetch(String roleName) {
        return adminsRepository.getAdminsByRoleNameWithFetch(roleName).stream()
                .map(adminsDbo -> AdminsDto.builder()
                        .id(adminsDbo.getId())
                        .level(adminsDbo.getLevel())
                        .createdTime(adminsDbo.getCreatedTime())
                        .status(adminsDbo.getStatus())
                        .updatedTime(adminsDbo.getUpdatedTime())
                        .email(adminsDbo.getEmail())
                        .firstName(adminsDbo.getFirstName())
                        .lastActivityTime(adminsDbo.getLastActivityTime())
                        .lastName(adminsDbo.getLastName())
                        .rolesId(adminsDbo.getRole().getId())
                        .build())
                .toList();
    }

    @Override
    public List<AdminsDtoPImpl> getAdminsWithLastPassCreation() {
        return adminsRepository.getAdminByLastPassCreation().stream()
                .map(adminsDtoP -> AdminsDtoPImpl.builder()
                        .adminId(adminsDtoP.getAdminId())
                        .lastName(adminsDtoP.getLastName())
                        .email(adminsDtoP.getEmail())
                        .getRoleName(adminsDtoP.getRoleName())
                        .firstName(adminsDtoP.getFirstName())
                        .passwordCreatedTime(adminsDtoP.getCreatedTime())
                        .build())
                .toList();
    }

        @Override
        public Page<AdminsDtoPImpl> getAdminsWithLastPassCreationPagination(AdminPageRequestDto adminPageRequestDto) {
            SortRequestDto sort = adminPageRequestDto.getSort();
            List<AdminsDtoPImpl> adminsDtoPS = adminsRepository.getAdminByLastPassCreationPagination(
                            PageRequest.of(adminPageRequestDto.getOffSet(), adminPageRequestDto.getLimit(),
                                    Sort.by(sort.getType(),
                                            sort.getField())))
                    .map(adminsDtoP -> AdminsDtoPImpl.builder()
                            .adminId(adminsDtoP.getAdminId())
                            .lastName(adminsDtoP.getLastName())
                            .email(adminsDtoP.getEmail())
                            .getRoleName(adminsDtoP.getRoleName())
                            .firstName(adminsDtoP.getFirstName())
                            .passwordCreatedTime(adminsDtoP.getCreatedTime())
                            .build())
                    .toList();

            return new PageImpl<>(adminsDtoPS);
    }
}
