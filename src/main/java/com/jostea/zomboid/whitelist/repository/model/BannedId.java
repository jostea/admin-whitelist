package com.jostea.zomboid.whitelist.repository.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bannedid")
public class BannedId {

    @Id
    private String steamid;

    private String reason;
}
