package com.turnosRegistro.shift.record.formsAndResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
public class TurnResponse {
    private Long id;
    private LocalTime startTurn;
    private LocalTime finishTurn;
}
