package com.turnosRegistro.shift.record.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @OneToMany
    private Collection<Reserve> reserves;
    private Collection<Company> companies;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:MM:ss")
    private LocalDateTime startTurn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime finishTurn;
    private Integer numberOfPlaces;
    private Boolean successfulBooking = Boolean.FALSE;
}
