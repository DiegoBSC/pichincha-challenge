package com.pichincha.transacctions.controller;

import com.pichincha.transacctions.dto.ReportMovementDto;
import com.pichincha.transacctions.service.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{clientId}")
    public List<ReportMovementDto> generateReport(@PathVariable UUID clientId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate){
        return reportService.getReportFromClientAndDate(clientId, startDate, endDate);
    }
}
