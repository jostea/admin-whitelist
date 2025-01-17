package com.jostea.zomboid.whitelist.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admins_ip")
public class AdminsIP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ip;
}
