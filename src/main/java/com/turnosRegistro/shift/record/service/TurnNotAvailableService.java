package com.turnosRegistro.shift.record.service;
import com.turnosRegistro.shift.record.dto.TurnNotAvailableDto;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;

public interface TurnNotAvailableService {
    TurnNotAvailable createTurnNotAvailable(TurnNotAvailable turnNotAvailable);
    TurnNotAvailableDto findDtoById(Long id);
    TurnNotAvailable findEntityById(Long id);
    void deleteById(Long id);
    void deleteEntityByReserve(Reserve reserve);
}
