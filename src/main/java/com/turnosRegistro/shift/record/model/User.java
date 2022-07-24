package com.turnosRegistro.shift.record.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.turnosRegistro.shift.record.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "can´t be empty or null")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "can´t be empty or null")
    private String password;
    @NotBlank(message = "can´t be empty or null")
    private String phoneNumber;
    @NotBlank(message = "can´t be empty or null")
    private String firstName;
    private String LastName;
    @CreationTimestamp
    private LocalDateTime creationDate;
    private Boolean deleted = Boolean.FALSE;
    private Role role = Role.CLIENT;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Reserve> reserveFavorite;
}
