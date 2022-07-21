package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.dto.TurnDto;
import com.turnosRegistro.shift.record.dto.mapper.TurnMapper;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.exception.NotFoundException;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.repository.ReserveRepository;
import com.turnosRegistro.shift.record.repository.TurnRepository;
import com.turnosRegistro.shift.record.service.CompanyService;
import com.turnosRegistro.shift.record.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
public class TurnServiceImpl implements TurnService {
    private static final int SIZE_PAGE = 10;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TurnRepository turnRepository;
    @Autowired
    private TurnMapper turnMapper;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private PaginationMessageHandler paginationMessage;
    @Override
    public TurnDto createTurn(Long idCompany, TurnDto turnDto, HttpServletRequest request) {
        Company company = companyService.findCompanyEntityById(idCompany, request);
        Turn turn = turnMapper.createTurnFromDto(turnDto);
        turn.setCompany(company);
        return turnMapper.entityToDto(turnRepository.save(turn));
    }

    @Override
    public TurnDto updateTurn(Long idTurn, TurnDto turnDto, HttpServletRequest request) {
        Turn turn = turnMapper.updateTurnFromDto(findEntityById(idTurn, request), turnDto);
        return turnMapper.entityToDto(turnRepository.save(turn));
    }
    @Override
    public Turn findEntityById(Long id, HttpServletRequest request) {
        Turn turn = turnRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
        companyService.findCompanyEntityById(turn.getCompany().getId(), request);
        return turn;
    }

    @Override
    public TurnDto findDtoById(Long id, HttpServletRequest request) {
        return turnMapper.entityToDto(findEntityById(id, request));
    }

    @Override
    public MessageInfo deleteById(Long id, HttpServletRequest request) {
        turnRepository.delete(findEntityById(id, request));
        return new MessageInfo(messageHandler.message("delete", String.valueOf(id)), HttpStatus.OK.value(), request.getRequestURL().toString());
    }

    @Override
    public MessagePagination turnsCompanyPage(String companyName, Integer page, HttpServletRequest request) {
        Page<Turn> turnPage = companyRepository.findTurnsPageByCompanyName(companyName, PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(turnPage, turnMapper.listTurnDtoFromEntityList(turnPage.getContent()), request);
    }
}
