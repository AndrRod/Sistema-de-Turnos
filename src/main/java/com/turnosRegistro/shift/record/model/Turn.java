package com.turnosRegistro.shift.record.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.turnosRegistro.shift.record.enums.Day;
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
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turn", fetch = FetchType.LAZY)
    private Collection<Reserve> reserves;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Day dayTurn;
    private LocalTime startTurn;
    private LocalTime finishTurn;
    private Integer numberOfPlaces;
}
