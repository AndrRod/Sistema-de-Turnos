package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReserveMapper {
    public ReserveDto entityToDto(Reserve reserve){
        return new ReserveDto(reserve.getId(), reserve.getUser(), reserve.getCompany(), reserve.getDateTurn(), reserve.getTurn());
    }
    public List<Object> listDtoFromListEntities(List<Reserve> reserveList){
        return reserveList.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
