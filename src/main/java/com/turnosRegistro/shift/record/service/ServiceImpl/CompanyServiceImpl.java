package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.dto.mapper.CompanyMapper;
import com.turnosRegistro.shift.record.exception.*;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.repository.ReserveRepository;
import com.turnosRegistro.shift.record.service.CompanyService;
import com.turnosRegistro.shift.record.service.ReserveService;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public MessagePagination findCompaniesPagination(Integer page, HttpServletRequest request) {
        deleteTurnsExpired();
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(companyPage, companyMapper.listDtoFromListEntites(companyPage.getContent()), request);
    }
    @Override
    public MessagePagination getAllUserRoleCompanyPageable(Integer page, HttpServletRequest request) {
        deleteTurnsExpired();
        Page<Company> pageList = companyRepository.findCompaniesByUser(userService.findUserLogedByEmail(request), PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(pageList, companyMapper.listDtoFromListEntites(pageList.getContent()), request);
    }
    @Override
    public void deleteTurnsExpired(){
        List<Turn> turns = companyRepository.findAllTurns();
        turns.stream().forEach(t-> t.getReserves().stream().forEach((r)->{
            System.out.println(r.getDateTurn());
            System.out.println(r.getDateTurn().isBefore(LocalDate.now()));
            if(r.getDateTurn().isBefore(LocalDate.now())) reserveRepository.delete(r);}));
    }
}
