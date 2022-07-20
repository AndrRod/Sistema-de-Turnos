package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.dto.ReservePartDto;
import com.turnosRegistro.shift.record.model.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReserveMapper {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private TurnMapper turnMapper;
    public ReserveDto entityToDto(Reserve reserve){
        return new ReserveDto(reserve.getId(), userMapper.entityToUserPartDto(reserve.getUser()), companyMapper.entityToCompanyPartDto(reserve.getCompany()), reserve.getDateTurn(), turnMapper.entityToPartDto(reserve.getTurn()));
    }
    public List<Object> listDtoFromListEntities(List<Reserve> reserveList){
        return reserveList.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public ReservePartDto entityToPartDto(Reserve reserve){
        return new ReservePartDto(reserve.getId(), reserve.getDateTurn(), turnMapper.entityToPartDto(reserve.getTurn()), userMapper.entityToUserPartDto(reserve.getUser()));
    }
    public Collection<ReservePartDto> listPartDtoFromListEntities(Collection<Reserve> reserveList){
        return reserveList.stream().map(this::entityToPartDto).collect(Collectors.toList());
    }

}
