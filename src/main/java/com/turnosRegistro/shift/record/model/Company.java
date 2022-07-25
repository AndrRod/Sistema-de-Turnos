package com.turnosRegistro.shift.record.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
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
    @Column(nullable = false, length = 4000)
    private String description;
    @NotBlank(message = "can't be null")
    private String email;
    @NotBlank(message = "can't be null")
    private String address;
    @NotBlank(message = "can't be null")
    private String logoImage;
    private String CBU;
    private Boolean deleted = Boolean.FALSE;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
    private Collection<Turn> turn = new HashSet<>();
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TurnNotAvailable> turnNotAviables = new HashSet<>();
}

