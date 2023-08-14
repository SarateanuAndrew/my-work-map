package com.example.myworkmap.model.dbo;

import com.example.myworkmap.model.enums.PasswordsStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static com.example.myworkmap.model.enums.PasswordsStatusEnum.ACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passwords")
public class PasswordsDbo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String passwordHash;

    @Builder.Default
    @Column(nullable = false, length = 200)
    @Enumerated(value = STRING)
    private PasswordsStatusEnum status = ACTIVE;

    private LocalDateTime disabledAt;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @CreationTimestamp
    private LocalDateTime createdTime;
    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_id")
    private AdminsDbo adminsDbo;
}