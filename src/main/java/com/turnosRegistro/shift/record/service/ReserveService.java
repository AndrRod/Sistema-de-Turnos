package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.Turn;

import javax.servlet.http.HttpServletRequest;

public interface ReserveService {
    ReserveDto createReserve(Long idTurn, ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request);
    ReserveDto updateReserve(Long idReserve, ReserveCreateOrUpdateDto reserveUpdateDto, HttpServletRequest request);
    Reserve findEntityById(Long id, HttpServletRequest request);
    ReserveDto findDtoById(Long id, HttpServletRequest request);
    MessageInfo deleteReserveById(Long id, HttpServletRequest request);
    MessagePagination reservesPaginationByCompany(String nameCompany, Integer page, HttpServletRequest request);
    boolean isTurnNotAvailable(Turn turn, Reserve reserve);
}
