package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.service.PlayersWhitelistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-players-whitelist")
public class AllowedPlayerRestController {

    private final PlayersWhitelistService playersWhitelistService;

    @PostMapping("/add/{username}")
    @Transactional
    public ResponseEntity<Void> addPlayer(@PathVariable("username") final String username) {
        playersWhitelistService.addPlayer(username);
        return ResponseEntity.ok().build();
    }
}
