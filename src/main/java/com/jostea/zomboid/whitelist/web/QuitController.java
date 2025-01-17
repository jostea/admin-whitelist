package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.QuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuitController {

    private final QuitService quitService;

    @GetMapping("/terminate-server")
    public ResponseEntity<String> quitServer() {
        quitService.stopServer();
        return ResponseEntity.ok("Server stopped");
    }
}
