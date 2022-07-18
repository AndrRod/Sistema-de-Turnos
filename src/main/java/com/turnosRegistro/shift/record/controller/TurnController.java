package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.formsAndResponses.CompanyNameForm;
import com.turnosRegistro.shift.record.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
@RestController
@RequestMapping("/turns")
public class TurnController {
    @Autowired
    private TurnService service;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TurnDto findById(@PathVariable String id, HttpServletRequest request){
        return service.findDtoById(Long.valueOf(id), request);
    }
    @PostMapping("/idcompany/{idCompany}")
    @ResponseStatus(HttpStatus.CREATED)
    public TurnDto createTurnByCompanyId(@PathVariable String idCompany, @RequestBody @Valid TurnDto turnDto, HttpServletRequest request){
        return service.createTurn(Long.valueOf(idCompany), turnDto, request);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TurnDto updateTurn(@PathVariable String id, @DateTimeFormat(pattern = "HH:mm:ss")  @RequestBody @Valid TurnDto turnDto, HttpServletRequest request){
        return service.updateTurn(Long.valueOf(id), turnDto, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageInfo deleteById(@PathVariable String id, HttpServletRequest request){
        return service.deleteById(Long.valueOf(id), request);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessagePagination turnsPaginationByCompanyName(@RequestParam String page, HttpServletRequest request, @RequestBody @Valid CompanyNameForm company){
        return service.turnsCompanyPage(company.getCompanyName(), Integer.valueOf(page), request);
    }
}

