package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.dto.TurnNotAvailableDto;
import com.turnosRegistro.shift.record.dto.mapper.TurnNotAviableMapper;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import com.turnosRegistro.shift.record.repository.TurnNotAvailableRepository;
import com.turnosRegistro.shift.record.service.TurnNotAvailableService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class TurnNotAvailableServiceImpl implements TurnNotAvailableService {
    @Autowired
    private TurnNotAvailableRepository repository;
    @Autowired
    private TurnNotAviableMapper turnNotAviableMapper;
    @Override
    public TurnNotAvailableDto createTurnNotAviable(TurnNotAvailableDto turnNotAviableDto, Company company) {
        TurnNotAvailable turnNotAvailable = turnNotAviableMapper.createEntityFromDto(turnNotAviableDto);
        turnNotAvailable.setCompany(company);
        return turnNotAviableMapper.entityToDto(repository.save(turnNotAvailable));
    }

    @Override
    public TurnNotAvailableDto findDtoById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public TurnNotAvailable findEntityById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public MessageInfo deleteById(Long id, HttpServletRequest request) {
        return null;
    }
}
