package com.jostea.zomboid.whitelist.repository.extension;

import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.stream.Collectors;

@RepositoryDefinition(domainClass = AllowedPlayer.class, idClass = Integer.class)
public interface AllowedPlayerRepository {

    List<AllowedPlayer> findAll();

    default List<String> getAllUsernames() {
        return findAll().stream()
                .map(AllowedPlayer::getUsername)
                .collect(Collectors.toList());
    }

    void save(AllowedPlayer player);

    void deleteByUsername(String player);
}
