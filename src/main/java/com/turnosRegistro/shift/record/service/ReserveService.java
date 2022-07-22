package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.Turn;

import javax.servlet.http.HttpServletRequest;

public interface ReserveService {
    ReserveDto createReserve(ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request);
    ReserveDto updateReserve(Long idReserve, ReserveCreateOrUpdateDto reserveUpdateDto, HttpServletRequest request);
    Reserve findEntityById(Long id, HttpServletRequest request);
    ReserveDto findDtoById(Long id, HttpServletRequest request);
    MessageInfo deleteReserveById(Long id, HttpServletRequest request);
    MessagePagination reservesPaginationByCompanyId(Long idCompany, Integer page, HttpServletRequest request);
    boolean isTurnNotAvailable(Turn turn, Reserve reserve);
}
