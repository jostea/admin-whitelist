package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.service.RestartService;
import com.jostea.zomboid.whitelist.service.domain.QuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuitController {

    private final QuitService quitService;

    @GetMapping("/quit-server")
    public ResponseEntity<String> quitServer() {
        quitService.stopServer();
        return ResponseEntity.ok("Server stopped");
    }
}
