package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.domain.repository.extension.AdminsIpRepository;
import com.jostea.zomboid.whitelist.domain.repository.extension.AllowedPlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersWhitelistService {

    private final AllowedPlayerRepository allowedPlayerRepository;

    private final AdminsIpRepository adminsIpRepository;

    public void savePlayer(String ip, AllowedPlayer allowedPlayer) {
        if (adminsIpRepository.existsByIp(ip)) {
            log.info("User with IP: {} saved player with username: {}",  ip, allowedPlayer.getUsername());
            allowedPlayerRepository.save(allowedPlayer);
        } else {
            log.info("User with IP: {} tried to save player with username: {}", ip, allowedPlayer.getUsername());
        }
    }

    public void deletePlayer(String ip, String username) {
        if (adminsIpRepository.existsByIp(ip)) {
            log.info("User with IP: {} removed player with username: {}", ip, username);
            allowedPlayerRepository.deleteByUsername(username);
        } else {
            log.info("User with IP: {} tried to remove player with username: {}", ip, username);
        }
    }

    public void addPlayer(String player) {
        final AllowedPlayer allowedPlayer = new AllowedPlayer();
        allowedPlayer.setUsername(player);

        log.info("Discord bot saved player with username: {}", allowedPlayer.getUsername());
        allowedPlayerRepository.save(allowedPlayer);
    }
}
