package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.dto.mapper.ReserveMapper;
import com.turnosRegistro.shift.record.enums.Day;
import com.turnosRegistro.shift.record.exception.BadRequestException;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.exception.NotFoundException;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.repository.ReserveRepository;
import com.turnosRegistro.shift.record.repository.TurnRepository;
import com.turnosRegistro.shift.record.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ReserveServiceImpl implements ReserveService {
    private static final int SIZE_PAGE = 10;
    @Autowired
    private UserService userService;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TurnRepository turnRepository;
    @Autowired
    private TurnService turnService;
    @Autowired
    private PaginationMessageHandler paginationMessageHandler;
    @Autowired
    private TurnNotAvailableService turnNotAvailableService;
    @Autowired
    private EmailService emailService;
    @Override
    public ReserveDto createReserve(ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request) {
        Turn turn = turnRepository.findById(reserveCreateDto.getIdTurn()).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(reserveCreateDto.getIdTurn()))));;
        if(!turn.getDayTurn().toString().equals(reserveCreateDto.getDateTurn().getDayOfWeek().toString())) throw new BadRequestException(messageHandler.message("reserve.error", reserveCreateDto.getDateTurn().getDayOfWeek().toString()));
        Reserve reserve = new Reserve(
                null,
                userService.findUserLogedByEmail(request),
                turn.getCompany(),
                reserveCreateDto.getDateTurn(),
                turn);
        turnNotAllowed(reserve.getDateTurn());
        if(isTurnNotAvailable(reserve.getTurn(), reserve))  throw new BadRequestException(messageHandler.message("not.reserve", null));
        if(isTheLastTurn(reserve.getTurn(), reserve)) {
            TurnNotAvailable turnNotAvailable = new TurnNotAvailable(null, Day.valueOf(String.valueOf(reserve.getDateTurn().getDayOfWeek())), reserve.getDateTurn(), reserve.getTurn().getStartTurn(), reserve.getTurn().getFinishTurn(), reserve.getCompany());
            turnNotAvailableService.createTurnNotAvailable(turnNotAvailable);
        }
//        emailService.sendEmail(reserve.getUser().getEmail(), "Hello " + reserve.getUser().getFirstName() +"."+messageHandler.message("reserve.success.email", reserve.getDateTurn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +" "+ reserve.getTurn().getStartTurn()), reserve.getCompany().getName());
        return reserveMapper.entityToDto(reserveRepository.save(reserve));
    }
    public boolean isTheLastTurn(Turn turn, Reserve reserve){
        if((turn.getNumberOfPlaces() -2) < turn.getReserves().stream().filter(r-> r.getDateTurn().equals(reserve.getDateTurn())).count()) return true;
        return false;
    }
    @Override
    public boolean isTurnNotAvailable(Turn turn, Reserve reserve){
        if((turn.getNumberOfPlaces()-1) < turn.getReserves().stream().filter(r-> r.getDateTurn().equals(reserve.getDateTurn())).count()) return true;
        return false;
    }
    public Turn findTurnById(Long id){
        return turnRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
    }
    @Override
    public ReserveDto updateReserve(Long idReserve, ReserveCreateOrUpdateDto reserveDto, HttpServletRequest request) {
        Reserve reserve = findEntityById(idReserve, request);
        if(!reserve.getTurn().getDayTurn().toString().equals(reserveDto.getDateTurn().getDayOfWeek().toString())) throw new BadRequestException(messageHandler.message("reserve.error", reserveDto.getDateTurn().getDayOfWeek().toString()));
        turnNotAllowed(reserve.getDateTurn());
        if(isTurnNotAvailable(reserve.getTurn(), reserve)){
            turnNotAvailableService.deleteEntityByReserve(reserve);
        }
        reserve.setTurn(findTurnById(reserveDto.getIdTurn()));
        reserve.setDateTurn(reserveDto.getDateTurn());
        if(isTheLastTurn(reserve.getTurn(), reserve)) {
            TurnNotAvailable turnNotAvailable = new TurnNotAvailable(null, Day.valueOf(String.valueOf(reserve.getDateTurn().getDayOfWeek())), reserve.getDateTurn(), reserve.getTurn().getStartTurn(), reserve.getTurn().getFinishTurn(), reserve.getCompany());
            turnNotAvailableService.createTurnNotAvailable(turnNotAvailable);
        }
//        emailService.sendEmail(reserve.getUser().getEmail(), "Hello " + reserve.getUser().getFirstName() +"."+ messageHandler.message("reserve.update.success.email", reserve.getDateTurn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +" "+ reserve.getTurn().getStartTurn()), reserve.getCompany().getName());
        return reserveMapper.entityToDto(reserveRepository.save(reserve));
    }
    void turnNotAllowed(LocalDate date){
        if(date.isBefore(LocalDate.now())) throw new BadRequestException(messageHandler.message("date.error", String.valueOf(date)));
    }

    @Override
    public Reserve findEntityById(Long id, HttpServletRequest request) {
        Reserve reserve = reserveRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
        userService.isAuthorizate(reserve.getUser(), request, reserve.getCompany());
        return reserve;
    }

    @Override
    public ReserveDto findDtoById(Long id, HttpServletRequest request) {
        return reserveMapper.entityToDto(findEntityById(id, request));
    }

    @Override
    public MessageInfo deleteReserveById(Long id, HttpServletRequest request) {
        Reserve reserve = findEntityById(id, request);
        turnNotAvailableService.deleteEntityByReserve(reserve);
        reserveRepository.delete(reserve);
        return new MessageInfo(messageHandler.message("delete", String.valueOf(id)), 200, request.getRequestURL().toString());
    }

    @Override
    public MessagePagination reservesPaginationByCompanyId(Long idCompany, Integer page, HttpServletRequest request) {
        Page<Reserve> reservePage = reserveRepository.findReserveByCompanyId(idCompany, PageRequest.of(page, SIZE_PAGE));
        return paginationMessageHandler.message(reservePage, reserveMapper.listDtoFromListEntities(reservePage.getContent()), request);
    }
}
