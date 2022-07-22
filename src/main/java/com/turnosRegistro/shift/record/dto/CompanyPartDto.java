package com.turnosRegistro.shift.record.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Setter @Getter @AllArgsConstructor
public class CompanyPartDto {
    private Long id;
    private String name;
    private Collection<TurnNotAvailableDto> turnNotAviables = new HashSet<>();

}
