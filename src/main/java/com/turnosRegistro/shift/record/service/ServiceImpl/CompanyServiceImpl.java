package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.dto.mapper.CompanyMapper;
import com.turnosRegistro.shift.record.exception.*;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.repository.ReserveRepository;
import com.turnosRegistro.shift.record.repository.TurnNotAvailableRepository;
import com.turnosRegistro.shift.record.service.CompanyService;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


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
    private PaginationMessageHandler paginationMessage;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private TurnNotAvailableRepository turnNotAvailableRepository;
    @Override
    public CompanyDto createCompany(CompanyDto companyDto, HttpServletRequest request) {
        Company company = companyMapper.createEntityFromDto(companyDto);
        company.setUserCompany(userService.findUserLogedByEmail(request));
        return companyMapper.entityToDto(companyRepository.save(company));
    }

    @Override
    public CompanyDto updateCompnay(Long idCompany, CompanyDto companyDto, HttpServletRequest request) {
        Company company = companyMapper.entityUpdateFromDto(findCompanyEntityById(idCompany, request), companyDto);
        return companyMapper.entityToDto(companyRepository.save(company));
    }

    @Override
    public Company findCompanyEntityById(Long id, HttpServletRequest request) {
        Company company = companyRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
        userService.isAuthorizate(company.getUserCompany(), request, company);
        return company;
    }

    @Override
    public CompanyDto findCompanyDtoById(Long id, HttpServletRequest request) {
        return companyMapper.entityToDto(findCompanyEntityById(id, request));
    }

    @Override
    public MessageInfo deleteCompany(Long id, HttpServletRequest request) {
        companyRepository.delete(findCompanyEntityById(id, request));
        return new MessageInfo(messageHandler.message("delete", String.valueOf(id)), HttpStatus.OK.value(), request.getRequestURL().toString());
    }
    @Transactional
    @Override
    public MessagePagination findCompaniesPagination(Integer page, HttpServletRequest request) {
        reserveRepository.deleteReserveExpired(LocalDate.now());
        turnNotAvailableRepository.deleteTurnNotAvailableExpired(LocalDate.now());
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(page, SIZE_PAGE));
        List<Object> companiesDto =  companyMapper.listDtoFromListEntites(companyPage.getContent());
        return paginationMessage.message(companyPage, companiesDto, request);
    }
    @Transactional
    @Override
    public MessagePagination getAllUserRoleCompanyPageable(Integer page, HttpServletRequest request) {
        reserveRepository.deleteReserveExpired(LocalDate.now());
        turnNotAvailableRepository.deleteTurnNotAvailableExpired(LocalDate.now());
        Page<Company> pageList = companyRepository.findCompaniesByUser(userService.findUserLogedByEmail(request), PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(pageList, companyMapper.listDtoFromListEntites(pageList.getContent()), request);
    }
}
