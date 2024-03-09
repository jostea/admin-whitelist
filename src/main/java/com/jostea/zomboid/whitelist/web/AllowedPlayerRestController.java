package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.PlayersWhitelistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
