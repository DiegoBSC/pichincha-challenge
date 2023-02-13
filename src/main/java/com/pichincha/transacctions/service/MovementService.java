package com.pichincha.transacctions.service;

import com.pichincha.transacctions.dto.MovementDto;

import java.util.List;
import java.util.UUID;

public interface MovementService {
    List<MovementDto> indexMovement();
    MovementDto createMovement(MovementDto movementDto);
    void deleteMovement(UUID movementId);
    MovementDto showMovementById(UUID movementId);
}
