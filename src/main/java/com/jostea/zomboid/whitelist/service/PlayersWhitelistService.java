package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.repository.domain.model.AdminsIP;
import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.repository.extension.AdminsIpRepository;
import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.support.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlayersWhitelistService {

    private final AllowedPlayerRepository allowedPlayerRepository;

    private final AdminsIpRepository adminsIpRepository;

    public void savePlayer(String ip, AllowedPlayer allowedPlayer) {

        for (AdminsIP adminIP : adminsIpRepository.findAll()) {
            if (ip.equals(adminIP.getIp())) {
                Logger.log("User with ip " + ip + " is trying to save player with username " + allowedPlayer.getUsername());
                allowedPlayerRepository.save(allowedPlayer);
            } else {
                Logger.log("User with ip " + ip + " tried to save player with username " + allowedPlayer.getUsername());
            }
        }
    }

    public void deletePlayer(String ip, String username) {
        for (AdminsIP adminIP : adminsIpRepository.findAll()) {
            if (ip.equals(adminIP.getIp())) {
                Logger.log("User with ip " + ip + " is trying to remove player with username " + username);
                allowedPlayerRepository.deleteByUsername(username);
            } else {
                Logger.log("User with ip " + ip + " tried to remove player with username " + username);
            }
        }

    }

}
