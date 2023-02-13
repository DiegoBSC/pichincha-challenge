package com.pichincha.transacctions.controller;

import com.pichincha.transacctions.dto.ClientDto;
import com.pichincha.transacctions.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/index")
    public List<ClientDto> index(){
        return clientService.indexClient();
    }

    @GetMapping("/show/{clientId}")
    public ClientDto showClient(@PathVariable UUID clientId){
        return clientService.showClientById(clientId);
    }

    @PostMapping("/create")
    public ClientDto createClient(@Valid @RequestBody ClientDto clientDto){
        return clientService.createClient(clientDto);
    }

    @PutMapping("/update/{clientId}")
    public ClientDto updateClient(@Valid @PathVariable UUID clientId,  @RequestBody ClientDto clientDto){
        return clientService.updateClient(clientId, clientDto);
    }

    @DeleteMapping("/delete/{clientId}")
    public void deleteClient(@PathVariable UUID clientId){
        clientService.deleteClient(clientId);
    }

}
