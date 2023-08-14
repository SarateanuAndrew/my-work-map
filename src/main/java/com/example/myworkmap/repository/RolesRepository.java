package com.example.myworkmap.repository;

import com.example.myworkmap.model.dbo.RolesDbo;
import com.example.myworkmap.model.dto.RolesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RolesRepository extends JpaRepository<RolesDbo, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE RolesDbo r " +
            "SET r.name = :#{#rolesDto.name}, " +
            "r.description = :#{#rolesDto.description} " +
//            "r.updatedTime = CURRENT_TIMESTAMP " +
            "WHERE r.id = :#{#rolesDto.id}")
    void updateRole(@Param("rolesDto") RolesDto rolesDto);
}
