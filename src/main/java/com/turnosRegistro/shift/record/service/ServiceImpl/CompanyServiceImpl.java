package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.dto.mapper.CompanyMapper;
import com.turnosRegistro.shift.record.exception.*;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.service.CompanyService;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class CompanyServiceImpl implements CompanyService {
    private static final int SIZE_PAGE = 10;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private UserService userService;
    @Autowired
    private PaginationMessage paginationMessage;
    @Override
    public CompanyDto createCompany(CompanyDto companyDto, HttpServletRequest request) {
        Company company = companyMapper.createEntityFromDto(companyDto);
        company.setUserCompany(userService.findUserLogedByEmail(request));
        return companyMapper.entityToDto(companyRepository.save(company));
    }

    @Override
    public CompanyDto updateCompnay(Long idCompany, CompanyDto companyDto, HttpServletRequest request) {
        Company company = companyMapper.entityUpdateFromDto(findCompanyEntityById(idCompany, request), companyDto);
        return companyMapper.entityToDto(company);
    }

    @Override
    public Company findCompanyEntityById(Long id, HttpServletRequest request) {
        Company company = companyRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", "by id: " + id)));
//        if(!company.getUserCompany().equals(userService.findUserLogedByEmail(request))) throw new BadRequestException(messageHandler.message("not.authorization", String.valueOf(id)));
        userService.isAuthorizate(company.getUserCompany(), request);
        return company;
    }

    @Override
    public CompanyDto findCompanyDtoById(Long id, HttpServletRequest request) {
        return companyMapper.entityToDto(findCompanyEntityById(id, request));
    }

    @Override
    public MessageInfo deleteCompany(Long id, HttpServletRequest request) {
        companyRepository.delete(findCompanyEntityById(id, request));
        return new MessageInfo(messageHandler.message("delete", "by id: " + id), HttpStatus.OK.value(), request.getRequestURL().toString());
    }

    @Override
    public MessagePagination findCompaniesPagination(Integer page, HttpServletRequest request) {
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(companyPage, companyMapper.listDtoFromListEntites(companyPage.getContent()), request);
    }
    @Override
    public MessagePagination getAllUserRoleCompanyPageable(Integer page, HttpServletRequest request) {
        Page<Company> pageList = companyRepository.findCompaniesByUser(userService.findUserLogedByEmail(request), PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(pageList, companyMapper.listDtoFromListEntites(pageList.getContent()), request);
    }
}