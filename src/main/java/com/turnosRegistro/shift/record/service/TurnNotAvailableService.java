package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.TurnNotAvailableDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;

import javax.servlet.http.HttpServletRequest;

public interface TurnNotAvailableService {
    TurnNotAvailableDto createTurnNotAviable(TurnNotAvailableDto turnNotAviableDto, Company company);
    TurnNotAvailableDto findDtoById(Long id, HttpServletRequest request);
    TurnNotAvailable findEntityById(Long id, HttpServletRequest request);
    MessageInfo deleteById(Long id, HttpServletRequest request);
}
