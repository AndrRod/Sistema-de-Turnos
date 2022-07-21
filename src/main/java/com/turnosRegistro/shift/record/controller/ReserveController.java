package com.turnosRegistro.shift.record.controller;
import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.CompanyNameForm;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/reserves")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ReserveDto getById(@PathVariable String id, HttpServletRequest request){
        return reserveService.findDtoById(Long.valueOf(id), request);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public MessageInfo deleteById(@PathVariable String id, HttpServletRequest request){
        return reserveService.deleteReserveById(Long.valueOf(id), request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ReserveDto updateReserve(@PathVariable String id, @Valid @RequestBody ReserveCreateOrUpdateDto reserveUpdateDto, HttpServletRequest request){
        return reserveService.updateReserve(Long.valueOf(id), reserveUpdateDto, request);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReserveDto createReserve(@RequestParam Long idTurn, @RequestBody @Valid ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request){
        return reserveService.createReserve(idTurn, reserveCreateDto, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{page}")
    public MessagePagination getReservesPagination(@RequestBody CompanyNameForm companyNameForm, @RequestParam Integer page, HttpServletRequest request){
        return reserveService.reservesPaginationByCompany(companyNameForm.getCompanyName(), page, request);
    }
}
