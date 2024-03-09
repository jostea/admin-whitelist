package com.jostea.zomboid.whitelist.domain.repository.extension;

import com.jostea.zomboid.whitelist.domain.model.PlayerAccessLevel;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RepositoryDefinition(domainClass = PlayerAccessLevel.class, idClass = Integer.class)
public interface PlayerAccessLevelRepository {

    List<PlayerAccessLevel> findAll();

    default Map<String, PlayerAccessLevel> findAllMap() {
        return findAll()
                .stream()
                .collect(Collectors.toMap(PlayerAccessLevel::getUsername, player -> player));
    }
}
