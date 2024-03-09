package com.jostea.zomboid.whitelist.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "whitelist")
public class Whitelist {

    @Id
    private int id;

    private String username;

    private int banned;

    @Column(name = "steamid")
    private String steamId;

    @Column(name = "accesslevel")
    private String accessLevel;
}
