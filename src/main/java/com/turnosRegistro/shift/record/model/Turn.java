package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class Turn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Reserve> reserves;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Company> companies;
    private LocalDateTime startTurn;
    private LocalDateTime finishTurn;
    private Integer numberOfPlaces;
    private Boolean successfulBooking = Boolean.FALSE;
}
