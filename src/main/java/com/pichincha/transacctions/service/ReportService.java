package com.pichincha.transacctions.service;

import com.pichincha.transacctions.dto.ReportMovementDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReportService {
    List<ReportMovementDto> getReportFromClientAndDate(UUID clientId, LocalDateTime starDate, LocalDateTime endDate);
}
