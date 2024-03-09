package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.service.RestartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestartController {

    private final RestartService restartService;

    @GetMapping("/restart-pz-server")
    public ResponseEntity<String> restart() {
        return ResponseEntity.ok(restartService.restartServer());
    }
}
