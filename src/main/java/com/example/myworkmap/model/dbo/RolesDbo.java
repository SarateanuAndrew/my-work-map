package com.example.myworkmap.model.dbo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RolesDbo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Name is obligatory!!!")
    @Column(nullable = false, length = 200)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @CreationTimestamp
    private LocalDateTime createdTime;
    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    @CreationTimestamp
    private LocalDateTime updatedTime;
}