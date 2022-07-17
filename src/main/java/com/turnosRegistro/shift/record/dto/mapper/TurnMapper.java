package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.model.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TurnMapper {
    @Autowired
    private CompanyMapper companyMapper;
    public Turn createTurnFromDto(TurnDto turnDto){
        return new Turn(null, null, null, turnDto.getStartTurn(), turnDto.getFinishTurn(), turnDto.getNumberOfPlaces(), null);
    }
    public TurnDto entityToDto(Turn turn){
        return new TurnDto(turn.getId(), turn.getReserves(), companyMapper.entityToCompanyPartDto(turn.getCompany()), turn.getStartTurn(), turn.getFinishTurn(), turn.getNumberOfPlaces(), turn.getSuccessfulBooking());
    }
    public List<Object> listTurnDtoFromEntityList(List<Turn> turns){
        return turns.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public Turn updateTurnFromDto(Turn turn, TurnDto turnDto){
        Optional.of(turn).ifPresent((t)->{
            if(turnDto.getNumberOfPlaces()!= null) t.setNumberOfPlaces(turnDto.getNumberOfPlaces());
            if(turnDto.getStartTurn()!= null) t.setStartTurn(turnDto.getStartTurn());
            if(turnDto.getFinishTurn()!=null) t.setFinishTurn((turnDto.getFinishTurn()));
            if(turnDto.getSuccessfulBooking()!=null) t.setSuccessfulBooking(turnDto.getSuccessfulBooking());
        });
        return turn;
    }
}
