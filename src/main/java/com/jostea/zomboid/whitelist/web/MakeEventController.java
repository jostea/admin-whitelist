package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/make-event")
public class MakeEventController {

    private final EventService eventService;

    @GetMapping("/{event}")
    public String gunshotEvent(@PathVariable String event) {
        return eventService.createEvent(event);
    }
}
