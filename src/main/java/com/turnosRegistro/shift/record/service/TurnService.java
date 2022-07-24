package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.formsAndResponses.TurnDateForm;
import com.turnosRegistro.shift.record.model.Turn;

import javax.servlet.http.HttpServletRequest;

public interface TurnService {
    TurnDto createTurn(Long idCompany, TurnDto turnDto, HttpServletRequest request);
    TurnDto updateTurn(Long idTurn, TurnDto turnDto, HttpServletRequest request);
    Turn findEntityById(Long id, HttpServletRequest request);
    TurnDto findDtoById(Long id, HttpServletRequest request);
    MessageInfo deleteById(Long id, HttpServletRequest request);
    MessagePagination turnsCompanyPage(Long idCompany, Integer page, HttpServletRequest request);
    MessagePagination turnsPageByDay(Long idCompany, TurnDateForm turndate, Integer page, HttpServletRequest request);
}
