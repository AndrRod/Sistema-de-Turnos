package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.model.Reserve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Collection;

@Getter @Setter @AllArgsConstructor
public class TurnDto {
    private Long id;
    private Collection<Reserve> reserves;
    private CompanyPartDto company;
    private LocalTime startTurn;
    private LocalTime finishTurn;
    private Integer numberOfPlaces;
    private Boolean successfulBooking;
}
