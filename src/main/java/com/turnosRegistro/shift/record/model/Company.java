package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@Data @Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE company SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userCompany;
    @Column(unique = true)
    private String name;
    @NotBlank(message = "can't be null")
    private String phoneNumber;
    private String description;
    @NotBlank(message = "can't be null")
    private String email;
    private String address;
    private String logoImage;
    @NotBlank(message = "can't be null")
    private String CBU;
    private Boolean deleted = Boolean.FALSE;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Turn> turn = new HashSet<>();
}

