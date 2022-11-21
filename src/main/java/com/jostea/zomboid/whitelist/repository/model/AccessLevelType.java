package com.jostea.zomboid.whitelist.repository.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessLevelType {

    ADMIN("admin"),
    MODERATOR("moderator");

    private final String typeName;
}
