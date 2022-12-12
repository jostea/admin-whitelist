package com.jostea.zomboid.whitelist.repository.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "player_access_level")
public class PlayerAccessLevel {

    @Id
    private int id;

    private String username;

    private String steamid;

    private String accesslevel;
}
