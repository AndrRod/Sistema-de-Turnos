package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class TurnNotAvailable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateTurn;
    private LocalTime startTurn;
    private LocalTime finishTurn;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
