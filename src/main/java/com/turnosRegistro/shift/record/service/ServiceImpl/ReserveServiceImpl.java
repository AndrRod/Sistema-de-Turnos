package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.dto.ReserveCreateOrUpdateDto;
import com.turnosRegistro.shift.record.dto.ReserveDto;
import com.turnosRegistro.shift.record.dto.mapper.ReserveMapper;
import com.turnosRegistro.shift.record.exception.BadRequestException;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.exception.NotFoundException;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.repository.CompanyRepository;
import com.turnosRegistro.shift.record.repository.ReserveRepository;
import com.turnosRegistro.shift.record.repository.TurnRepository;
import com.turnosRegistro.shift.record.service.CompanyService;
import com.turnosRegistro.shift.record.service.ReserveService;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
    private PaginationMessageHandler paginationMessageHandler;
    @Override
    public ReserveDto createReserve(ReserveCreateOrUpdateDto reserveCreateDto, HttpServletRequest request) {
        Turn turn = findTurnById(reserveCreateDto.getIdTurn());
        Reserve reserve = new Reserve(
                null,
                userService.findUserLogedByEmail(request),
                companyRepository.findByName(reserveCreateDto.getCompanyName()),
                reserveCreateDto.getDateTurn(),
                turn
        );
        if(turn.getNumberOfPlaces().equals(turnRepository.countReserves(turn.getId()))) {
//            turn.getReserves().stream().map(r-> r.getTurn().equals())
            throw new BadRequestException("no es posible hacer reserva se agotaron");
        }
        return reserveMapper.entityToDto(reserveRepository.save(reserve));
    }
    public Turn findTurnById(Long id){
        return turnRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
    }

    @Override
    public ReserveDto updateReserve(Long idReserve, ReserveCreateOrUpdateDto reserveDto, HttpServletRequest request) {
        Reserve reserve = findEntityById(idReserve, request);
        reserve.setTurn(findTurnById(reserveDto.getIdTurn()));
        reserve.setDateTurn(reserveDto.getDateTurn());
        return null;
    }

    @Override
    public Reserve findEntityById(Long id, HttpServletRequest request) {
        Reserve reserve = reserveRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
        userService.isAuthorizate(reserve.getUser(), request);
        return reserve;
    }

    @Override
    public ReserveDto findDtoById(Long id, HttpServletRequest request) {
        return reserveMapper.entityToDto(findEntityById(id, request));
    }

    @Override
    public MessageInfo deleteById(Long id, HttpServletRequest request) {
        reserveRepository.delete(findEntityById(id, request));
        return new MessageInfo(messageHandler.message("delete", String.valueOf(id)), 200, request.getRequestURL().toString());
    }

    @Override
    public MessagePagination reservesPaginationByCompany(String nameCompany, Integer page, HttpServletRequest request) {
        Page<Reserve> reservePage = reserveRepository.findReserveByCompany(nameCompany, PageRequest.of(page, SIZE_PAGE));
        return paginationMessageHandler.message(reservePage, reserveMapper.listDtoFromListEntities(reservePage.getContent()), request);
    }
}
