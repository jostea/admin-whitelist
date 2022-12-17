package com.jostea.zomboid.whitelist.repository.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "allowed_player")
public class AllowedPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^[\\w\\d\\s_\\-]{1,50}$", message = "Username is not valid")
    private String username;
}
