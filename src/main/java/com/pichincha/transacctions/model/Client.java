package com.pichincha.transacctions.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Client extends Person {
    private UUID id;
    private String password;
    private Boolean state;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Account> accounts;
}
