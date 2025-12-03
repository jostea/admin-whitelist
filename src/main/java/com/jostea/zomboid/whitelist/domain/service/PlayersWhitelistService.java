package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.domain.repository.extension.AllowedPlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersWhitelistService {

    private final AllowedPlayerRepository allowedPlayerRepository;


    public void savePlayer(String ip, AllowedPlayer allowedPlayer) {
        log.info("User with IP: {} saved player with username: {}", ip, allowedPlayer.getUsername());
        allowedPlayerRepository.save(allowedPlayer);
    }

    public void deletePlayer(String ip, String username) {
        log.info("User with IP: {} removed player with username: {}", ip, username);
        allowedPlayerRepository.deleteByUsername(username);
    }

    public void addPlayer(String player) {
        final AllowedPlayer allowedPlayer = new AllowedPlayer();
        allowedPlayer.setUsername(player);

        log.info("Discord bot saved player with username: {}", allowedPlayer.getUsername());
        allowedPlayerRepository.save(allowedPlayer);
    }
}
