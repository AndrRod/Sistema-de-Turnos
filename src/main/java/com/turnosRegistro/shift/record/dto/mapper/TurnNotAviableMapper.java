package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.TurnNotAvailableDto;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TurnNotAviableMapper {
    public TurnNotAvailable createEntityFromDto(TurnNotAvailableDto turnNotAviableDto){
        return new TurnNotAvailable(null, turnNotAviableDto.getDateTurn(),turnNotAviableDto.getStartTurn(), turnNotAviableDto.getFinishTurn(), null);
    }
    public TurnNotAvailableDto entityToDto(TurnNotAvailable turnNotAviable){
        return new TurnNotAvailableDto(turnNotAviable.getId(), turnNotAviable.getDateTurn(), turnNotAviable.getStartTurn(), turnNotAviable.getFinishTurn());
    }
    public Collection<TurnNotAvailableDto> listDtoFromListEntity(Collection<TurnNotAvailable> turnNotAviables){
        return turnNotAviables.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
