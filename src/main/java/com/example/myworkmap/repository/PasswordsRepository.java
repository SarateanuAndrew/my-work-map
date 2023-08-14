package com.example.myworkmap.repository;

import com.example.myworkmap.model.dbo.PasswordsDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordsRepository extends JpaRepository<PasswordsDbo, Long> {
    @Query("SELECT p " +
            "FROM PasswordsDbo p " +
            "JOIN FETCH p.adminsDbo a")
    List<PasswordsDbo> findAllPasswords();
}