package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.dto.ClientDto;
import com.pichincha.transacctions.model.Client;
import com.pichincha.transacctions.repository.ClientRepository;
import com.pichincha.transacctions.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ClientDto> indexClient() {
        return clientRepository.findAll().stream().map(client -> modelMapper.map(client, ClientDto.class)
        ).toList();
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDto.class);
    }

    @Override
    public ClientDto updateClient(UUID clientId, ClientDto clientDto) {
        Client clientSource = getClientById(clientId);
        modelMapper.map(clientDto, clientSource);
        clientSource.setId(clientId);
        return modelMapper.map(clientRepository.save(clientSource), ClientDto.class);
    }

    @Override
    public void deleteClient(UUID clientId) {
        Client clientSource = getClientById(clientId);
        clientRepository.delete(clientSource);
    }

    @Override
    public ClientDto showClientById(UUID clientId) {
        Client clientSource = getClientById(clientId);
        return modelMapper.map(clientSource, ClientDto.class);
    }

    private Client getClientById(UUID clientId){
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no existe"));
    }
}
