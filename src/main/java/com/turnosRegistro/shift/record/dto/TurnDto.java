package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Collection;

@Getter @Setter @AllArgsConstructor
public class TurnDto {
    private Long id;
    private Collection<ReservePartDto> reserves;
    private Day dayTurn;
    private CompanyPartDto company;
    private LocalTime startTurn;
    private LocalTime finishTurn;
    private Integer numberOfPlaces;
}
