package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.repository.extension.AdminsIpRepository;
import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayersWhitelistService {

    private final AllowedPlayerRepository allowedPlayerRepository;

    private final AdminsIpRepository adminsIpRepository;

    public void savePlayer(String ip, AllowedPlayer allowedPlayer) {
        if (adminsIpRepository.existsByIp(ip)) {
            log.info("User with ip " + ip + " saved player with username " + allowedPlayer.getUsername());
            allowedPlayerRepository.save(allowedPlayer);
        } else {
            log.info("User with ip " + ip + " tried to save player with username " + allowedPlayer.getUsername());
        }
    }

    public void deletePlayer(String ip, String username) {
        if (adminsIpRepository.existsByIp(ip)) {
            log.info("User with ip " + ip + " removed player with username " + username);
            allowedPlayerRepository.deleteByUsername(username);
        } else {
            log.info("User with ip " + ip + " tried to remove player with username " + username);
        }
    }

    public void addPlayer(String player) {
        AllowedPlayer allowedPlayer = new AllowedPlayer();
        allowedPlayer.setUsername(player);

        log.info("Discord bot saved player with username " + allowedPlayer.getUsername());
        allowedPlayerRepository.save(allowedPlayer);
    }
}
