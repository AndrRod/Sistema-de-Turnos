package com.turnosRegistro.shift.record.model;

import com.turnosRegistro.shift.record.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String LastName;
    @CreationTimestamp
    private LocalDateTime creationDate;
    private Boolean deleted = Boolean.FALSE;
    private Role role = Role.CLIENT;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Reserve> reserveFavorite;
}
