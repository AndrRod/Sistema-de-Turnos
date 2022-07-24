package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.dto.TurnPartDto;
import com.turnosRegistro.shift.record.formsAndResponses.TurnResponse;
import com.turnosRegistro.shift.record.model.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TurnMapper {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ReserveMapper reserveMapper;
    public Turn createTurnFromDto(TurnDto turnDto){
        return new Turn(null, new HashSet<>(), null,turnDto.getDayTurn(), turnDto.getStartTurn(), turnDto.getFinishTurn(), turnDto.getNumberOfPlaces());
    }
    public TurnDto entityToDto(Turn turn){
        return new TurnDto(turn.getId(), reserveMapper.listPartDtoFromListEntities(turn.getReserves()), turn.getDayTurn(), companyMapper.entityToCompanyPartDto(turn.getCompany()), turn.getStartTurn(), turn.getFinishTurn(), turn.getNumberOfPlaces());
    }

    public List<Object> listTurnDtoFromEntityList(List<Turn> turns){
        return turns.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public TurnPartDto entityToPartDto(Turn turn){
        return new TurnPartDto(turn.getId(), turn.getDayTurn(), turn.getStartTurn(), turn.getFinishTurn(), turn.getNumberOfPlaces());
    }
    public Collection<TurnPartDto> listPartDtoFromEntitiesList(Collection<Turn> turns){
        return turns.stream().map(t-> entityToPartDto(t)).collect(Collectors.toList());
    }
    public TurnResponse turnToTurnResponse(Turn turn){
        return new TurnResponse(turn.getId(), turn.getStartTurn(), turn.getFinishTurn());
    }
    public List<Object> turnPageToTurnsRespnonseList(List<Turn> turns){
        return turns.stream().map(this::turnToTurnResponse).collect(Collectors.toList());
    }
    public Turn updateTurnFromDto(Turn turn, TurnDto turnDto){
        Optional.of(turn).ifPresent((t)->{
            if(turnDto.getNumberOfPlaces()!= null) t.setNumberOfPlaces(turnDto.getNumberOfPlaces());
            if(turnDto.getStartTurn()!= null) t.setStartTurn(turnDto.getStartTurn());
            if(turnDto.getFinishTurn()!=null) t.setFinishTurn((turnDto.getFinishTurn()));
        });
        return turn;
    }
}
