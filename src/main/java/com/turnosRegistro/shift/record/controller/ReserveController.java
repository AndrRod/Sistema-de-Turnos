package com.turnosRegistro.shift.record.controller;
import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
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
    public ReserveDto createReserve(@RequestBody @Valid ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request){
        return reserveService.createReserve(reserveCreateDto, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/idCompany/{idCompany}")
    public MessagePagination getReservesPagination(@PathVariable String idCompany, @RequestParam Integer page, HttpServletRequest request){
        return reserveService.reservesPaginationByCompanyId(Long.valueOf(idCompany), page, request);
    }
}
