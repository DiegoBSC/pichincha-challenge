package com.pichincha.transacctions.service;

import com.pichincha.transacctions.dto.ClientDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<ClientDto> indexClient();
    ClientDto createClient(ClientDto clientDto);
    ClientDto updateClient(UUID clientId, ClientDto clientDto);
    void deleteClient(UUID clientId);
    ClientDto showClientById(UUID clientId);
}
