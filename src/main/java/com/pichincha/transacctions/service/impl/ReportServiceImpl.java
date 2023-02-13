package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.ReportMovementDto;
import com.pichincha.transacctions.model.Movement;
import com.pichincha.transacctions.repository.MovementRepository;
import com.pichincha.transacctions.service.ReportService;
import com.pichincha.transacctions.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MovementRepository movementRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ReportMovementDto> getReportFromClientAndDate(UUID clientId, LocalDateTime starDate, LocalDateTime endDate) {
        if(!validateRangeDate(starDate,endDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rango de fechas es invalido");

        List<Movement> movements = movementRepository.findMovementsByClientAndDate(clientId,
                DateUtils.setTimeFromLocalNowStart(starDate),
                DateUtils.setTimeFromLocalNowEnd(endDate)
        );

        List<ReportMovementDto> reportMovements = new ArrayList<>();

        movements.forEach(movement -> {
            ReportMovementDto reportMovementDto =ReportMovementDto.builder()
                    .transactionDate(movement.getMovementDate())
                    .clientName( movement.getAccount().getClient().getName())
                    .accountNumber(movement.getAccount().getAccountNumber())
                    .accountType(movement.getAccount().getType())
                    .initialBalance(movement.getBalanceInitial())
                    .balanceAvailable(movement.getBalanceAvailable())
                    .movementValue(movement.getMovementValue())
                    .state(movement.getAccount().getState())
                    .build();
            reportMovements.add(reportMovementDto);
        });
        return reportMovements;
    }

    private Boolean validateRangeDate(LocalDateTime startDate, LocalDateTime endDate){
        LocalDateTime dateInitial = DateUtils.setTimeFromLocalNowStart(startDate);
        LocalDateTime dateFinal = DateUtils.setTimeFromLocalNowEnd(endDate);
        int comparison = dateInitial.compareTo(dateFinal);

        return comparison <= 0;
    }
}
