package com.jostea.zomboid.whitelist.service.domain;

import lombok.Data;

import java.util.Set;

@Data
public class CompositeBanSet {

    private Set<String> steamIdSet;

    private Set<String> nicknameSet;
}
