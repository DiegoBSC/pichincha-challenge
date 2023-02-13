package com.pichincha.transacctions.dto;

import com.pichincha.transacctions.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Integer age;

    private String identification;
    private String address;

    private String telephone;
}
