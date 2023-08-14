package com.example.myworkmap.model.dbo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class AdminsDbo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @ManyToOne(fetch = LAZY)
    private RolesDbo role;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @CreationTimestamp
    private LocalDateTime lastActivityTime;

    @Column(length = 50)
    private String status;

    @OneToMany(fetch = LAZY, mappedBy = "adminsDbo", cascade = {PERSIST, MERGE, REMOVE})
    private List<PasswordsDbo> passwordsDbos;
}