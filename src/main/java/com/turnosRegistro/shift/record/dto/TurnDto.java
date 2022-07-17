package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.model.Reserve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter @Setter @AllArgsConstructor
public class TurnDto {
    private Long id;
    private Collection<Reserve> reserves;
    private CompanyPartDto company;
    private LocalDateTime startTurn;
    private LocalDateTime finishTurn;
    private Integer numberOfPlaces;
    private Boolean successfulBooking;
}
