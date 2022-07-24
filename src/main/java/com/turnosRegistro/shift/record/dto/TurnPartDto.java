package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.enums.Day;
import com.turnosRegistro.shift.record.model.Reserve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter @Getter @AllArgsConstructor
public class TurnPartDto {
    private Long id;
    private Day dayTurn;
    private LocalTime startTurn;
    private LocalTime finishTurn;
    private Integer numberOfPlaces;
}
