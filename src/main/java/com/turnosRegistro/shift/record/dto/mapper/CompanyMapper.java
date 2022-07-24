package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.dto.CompanyPartDto;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TurnMapper turnMapper;
    @Autowired
    private TurnNotAviableMapper turnNotAviableMapper;
    @Autowired
    private TurnRepository turnRepository;
    public Company createEntityFromDto(CompanyDto companyDto){
        return new Company(null, null, companyDto.getName(), companyDto.getPhoneNumber(), companyDto.getDescription(), companyDto.getEmail(), companyDto.getAddress(), companyDto.getLogoImage(), companyDto.getCBU(), false, new HashSet<>(), new HashSet<>());
    }
    public CompanyDto entityToDto(Company company){
        return new CompanyDto(company.getId(), userMapper.entityToUserPartDto(company.getUserCompany()), company.getName(), company.getPhoneNumber(), company.getDescription(), company.getEmail(), company.getAddress(), company.getLogoImage(), company.getCBU(), turnNotAviableMapper.listDtoFromListEntity(company.getTurnNotAviables()), turnRepository.findDaysAvailableByIdCompany(company.getId()));
    }
    public Company entityUpdateFromDto(Company company, CompanyDto companyDto){
        Optional.of(company).stream().forEach((c)->{
                if(companyDto.getAddress()!=null) c.setAddress(companyDto.getAddress());
                if(companyDto.getCBU()!=null) c.setCBU(companyDto.getCBU());
                if(companyDto.getDescription()!=null) c.setDescription(companyDto.getDescription());
                if(companyDto.getPhoneNumber()!=null) c.setPhoneNumber(companyDto.getPhoneNumber());
                if(companyDto.getName()!=null) c.setName(companyDto.getName());
                if(companyDto.getCBU()!=null) c.setCBU(companyDto.getCBU());
                if(companyDto.getLogoImage()!=null) c.setLogoImage(companyDto.getLogoImage());
                if(companyDto.getEmail()!=null) c.setEmail(companyDto.getEmail());
        });
        return company;
    }
    public List<Object> listDtoFromListEntites(List<Company> companyList){
        return companyList.stream().map(c-> entityToDto(c)).collect(Collectors.toList());
    }
    public CompanyPartDto entityToCompanyPartDto(Company company){
        return new CompanyPartDto(company.getId(), company.getName(), turnNotAviableMapper.listDtoFromListEntity(company.getTurnNotAviables()));
    }
}
