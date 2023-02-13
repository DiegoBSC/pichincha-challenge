package com.pichincha.transacctions.model;

import com.pichincha.transacctions.enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "persons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @NotNull
    private Integer age;
    @NotNull
    private String identification;
    @NotNull
    private String address;
    @NotNull
    private String telephone;
}
