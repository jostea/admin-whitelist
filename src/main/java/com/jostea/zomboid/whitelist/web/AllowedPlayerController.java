package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/allowed-player")
public class AllowedPlayerController {

    private static final String RESPONSE = "OK";

    private final AllowedPlayerRepository allowedPlayerRepository;

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok(allowedPlayerRepository.getAllUsernames());
    }

    @GetMapping("/save")
    @Transactional
    public ResponseEntity<String> savePlayer(@RequestParam final String username) {
        final AllowedPlayer allowedPlayer = new AllowedPlayer();
        allowedPlayer.setUsername(username);
        allowedPlayerRepository.save(allowedPlayer);

        return ResponseEntity.ok(RESPONSE);
    }

    @GetMapping("/delete")
    @Transactional
    public ResponseEntity<String> deletePlayer(@RequestParam final String username) {
        allowedPlayerRepository.deleteByUsername(username);
        return ResponseEntity.ok(RESPONSE);
    }
}
