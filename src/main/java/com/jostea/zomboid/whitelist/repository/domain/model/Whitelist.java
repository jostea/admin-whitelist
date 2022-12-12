package com.jostea.zomboid.whitelist.repository.domain.model;

import lombok.Data;

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

    private String steamid;

    private String accesslevel;
}
