package com.jostea.zomboid.whitelist.repository.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "allowed_player")
public class AllowedPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
}
