package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.PlayersAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get-amount-of-players")
public class PlayersNumberRestController {

    private final PlayersAmountService playersAmountService;

    @GetMapping
    @Transactional
    private String getAmountOfPlayers() {
        return playersAmountService.getAmountOfPlayers();
    }
}
