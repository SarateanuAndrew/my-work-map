package com.example.myworkmap.repository;

import com.example.myworkmap.model.dbo.AdminsDbo;
import com.example.myworkmap.model.projections.AdminsDtoP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AdminsRepository extends JpaRepository<AdminsDbo, Long> {
    @Query("SELECT a FROM AdminsDbo a JOIN a.role r WHERE r.name LIKE :name")
    List<AdminsDbo> getAdminsByRoleName(@Param("name") String name);

    @Query("SELECT a FROM AdminsDbo a JOIN FETCH a.role r WHERE r.name LIKE :name")
    List<AdminsDbo> getAdminsByRoleNameWithFetch(@Param("name") String name);

    @Query("SELECT a.firstName as firstName," +
            "a.lastName as lastName," +
            "a.email as email," +
            "a.role.name as roleName, " +
            "max(p.createdTime) as createdTime, " +
            "a.id as adminId " +
            "from AdminsDbo a " +
            "left join PasswordsDbo p on p.adminsDbo = a " +
            "group by firstName, lastName, email, adminId, roleName")
    List<AdminsDtoP> getAdminByLastPassCreation();

    @Query("SELECT a " +
            "FROM AdminsDbo a " +
            "    JOIN FETCH a.passwordsDbos " +
            "    JOIN FETCH a.role")
    List<AdminsDbo> findAllAdmins();

    @Query("SELECT a " +
            "FROM AdminsDbo a " +
            "    JOIN FETCH a.passwordsDbos " +
            "    JOIN FETCH a.role " +
            "WHERE a.id = :id")
    Optional<AdminsDbo> findAdminById(@Param("id") Long id);

    @Query(value = "SELECT a.firstName as firstName," +
            "a.lastName as lastName," +
            "a.email as email," +
            "a.role.name as roleName, " +
            "max(p.createdTime) as createdTime, " +
            "a.id as adminId " +
            "from AdminsDbo a " +
            "left join PasswordsDbo p on p.adminsDbo = a " +
            "group by firstName, lastName, email, adminId, roleName",
            countQuery = "select a from AdminsDbo a")
    Page<AdminsDtoP> getAdminByLastPassCreationPagination(Pageable pageable);
}