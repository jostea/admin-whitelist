package com.jostea.zomboid.whitelist.repository.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin_ip")
public class AdminsIP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ip;
}
