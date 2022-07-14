package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<PhoneNumber> phoneNumber = new HashSet<>();;
    private String description;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Address> address = new HashSet<>();
    private String logoImage;
    private String CBU;
    private Boolean deleted = Boolean.FALSE;
}

