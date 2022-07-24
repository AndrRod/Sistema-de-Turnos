package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.dto.CompanyDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService service;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompanyDto createCompany(@RequestBody @Valid CompanyDto companyDto, HttpServletRequest request){
        return service.createCompany(companyDto, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CompanyDto updateCompanyById(@PathVariable String id, @RequestBody @Valid CompanyDto companyDto, HttpServletRequest request){
        return service.updateCompnay(Long.valueOf(id), companyDto, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable Long id, HttpServletRequest request){
        return service.findCompanyDtoById(id, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public MessageInfo deleteById(@PathVariable String id, HttpServletRequest request){
        return service.deleteCompany(Long.valueOf(id), request);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MessagePagination companiesPagByPage(@RequestParam String page, HttpServletRequest request){
        return service.findCompaniesPagination(Integer.valueOf(page), request);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    public MessagePagination companiesPageOnlyUserCompanyRoleLogged(@RequestParam String page, HttpServletRequest request){
        return service.getAllUserRoleCompanyPageable(Integer.valueOf(page), request);
    }
}
