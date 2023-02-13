package com.pichincha.transacctions.controller;

import com.pichincha.transacctions.dto.MovementDto;
import com.pichincha.transacctions.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movement")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/index")
    public List<MovementDto> index(){
        return movementService.indexMovement();
    }

    @GetMapping("/show/{movementId}")
    public MovementDto showMovement(@PathVariable UUID movementId){
        return movementService.showMovementById(movementId);
    }

    @PostMapping("/create")
    public MovementDto createMovement(@Valid @RequestBody MovementDto movementDto){
        return movementService.createMovement(movementDto);
    }

    @DeleteMapping("/delete/{movementId}")
    public void deleteMovement(@PathVariable UUID movementId){
        movementService.deleteMovement(movementId);
    }

}
