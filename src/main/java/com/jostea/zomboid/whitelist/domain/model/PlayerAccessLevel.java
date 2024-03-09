package com.jostea.zomboid.whitelist.domain.model;

import lombok.Data;

import javax.persistence.Column;
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

    @Column(name = "steamid")
    private String steamId;

    @Column(name = "accesslevel")
    private String accessLevel;
}
