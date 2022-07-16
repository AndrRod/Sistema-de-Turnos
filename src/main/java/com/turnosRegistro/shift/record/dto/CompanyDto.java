package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Getter @Setter @AllArgsConstructor
public class CompanyDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private User userCompany;
    private String name;
    private String phoneNumber;
    private String description;
    private String email;
    private String address;
    private String logoImage;
    private String CBU;
    private Collection<Turn> turn = new HashSet<>();
}
