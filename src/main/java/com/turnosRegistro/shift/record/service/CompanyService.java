package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Company;

import javax.servlet.http.HttpServletRequest;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto, HttpServletRequest request);
    CompanyDto updateCompnay(Long idCompany, CompanyDto companyDto, HttpServletRequest request);
    Company findCompanyEntityById(Long id, HttpServletRequest request);
    CompanyDto findCompanyDtoById(Long id, HttpServletRequest request);
    MessageInfo deleteCompany(Long id, HttpServletRequest request);
    MessagePagination findCompaniesPagination(Integer page, HttpServletRequest request);
    MessagePagination getAllUserRoleCompanyPageable(Integer page, HttpServletRequest request);
}
