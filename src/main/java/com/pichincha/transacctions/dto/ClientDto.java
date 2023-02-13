package com.pichincha.transacctions.dto;

import com.pichincha.transacctions.model.Person;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends PersonDto {

    private UUID id;
    private String password;
    private Boolean state;
}
