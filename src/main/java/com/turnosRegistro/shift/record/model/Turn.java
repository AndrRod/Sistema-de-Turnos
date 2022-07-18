package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class Turn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turn")
    private Collection<Reserve> reserves;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(unique = true)
    private LocalTime startTurn;
    @Column(unique = true)
    private LocalTime finishTurn;
    private Integer numberOfPlaces;
    private Boolean successfulBooking = Boolean.FALSE;
}
