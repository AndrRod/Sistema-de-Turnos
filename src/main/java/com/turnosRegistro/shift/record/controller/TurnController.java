package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.formsAndResponses.CompanyNameForm;
import com.turnosRegistro.shift.record.formsAndResponses.TurnDateForm;
import com.turnosRegistro.shift.record.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
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
    @GetMapping("/idCompanyAllTurns/{idCompany}")
    @ResponseStatus(HttpStatus.OK)
    public MessagePagination allTurnsPaginationByCompanyId(@RequestParam String page, HttpServletRequest request, @PathVariable String idCompany){
        return service.turnsCompanyPage(Long.valueOf(idCompany), Integer.valueOf(page), request);
    }
    @PostMapping("/idCompany/{idCompany}")
    @ResponseStatus(HttpStatus.OK)
    public MessagePagination turnsPaginationByDay(@RequestParam String page, HttpServletRequest request, @RequestBody @Valid TurnDateForm turnDateForm, @PathVariable String idCompany){
        return service.turnsPageByDay(Long.valueOf(idCompany), turnDateForm, Integer.valueOf(page), request);
    }
}

