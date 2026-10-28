package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.SaveServerStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/save-server")
public class SaveServerController {

    private final SaveServerStateService saveServerStateService;

    @GetMapping()
    public String saveServer() {
        return saveServerStateService.save();
    }
}
