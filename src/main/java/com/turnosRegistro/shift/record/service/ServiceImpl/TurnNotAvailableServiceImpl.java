package com.turnosRegistro.shift.record.service.ServiceImpl;
import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.dto.TurnNotAvailableDto;
import com.turnosRegistro.shift.record.dto.mapper.TurnNotAviableMapper;
import com.turnosRegistro.shift.record.exception.BadRequestException;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import com.turnosRegistro.shift.record.repository.TurnNotAvailableRepository;
import com.turnosRegistro.shift.record.service.ReserveService;
import com.turnosRegistro.shift.record.service.TurnNotAvailableService;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TurnNotAvailableServiceImpl implements TurnNotAvailableService {
    @Autowired
    private TurnNotAvailableRepository repository;
    @Autowired
    private TurnNotAviableMapper turnNotAviableMapper;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ReserveService reserveService;
    @Override
    public TurnNotAvailable createTurnNotAvailable(TurnNotAvailable turnNotAvailable) {
        return repository.save(turnNotAvailable);
    }

    @Override
    public TurnNotAvailableDto findDtoById(Long id) {
        return turnNotAviableMapper.entityToDto(findEntityById(id));
    }
    @Override
    public TurnNotAvailable findEntityById(Long id) {
        return repository.findById(id).orElseThrow(()-> new BadRequestException(messageHandler.message("not.found", String.valueOf(id))));
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(findEntityById(id));
    }

    @Override
    public void deleteEntityByReserve(Reserve reserve){
        Try.of(()-> {TurnNotAvailable turnNotAvailable = repository.findAll().stream().filter(tn->
                        tn.getDateTurn().equals(reserve.getDateTurn()) &&
                        tn.getFinishTurn().equals(reserve.getTurn().getFinishTurn()) &&
                        tn.getStartTurn().equals(reserve.getTurn().getStartTurn()) &&
                        tn.getDateTurn().equals(reserve.getTurn().getDayTurn())).collect(Collectors.toList()).get(0);
                    repository.delete(turnNotAvailable);
                    return null;
                }
        );
    }
}
