package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.GiveItemService;
import com.jostea.zomboid.whitelist.domain.service.ItemCatalogService;
import com.jostea.zomboid.whitelist.domain.service.PlayersAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/give-item")
public class GiveItemController {

    private final GiveItemService giveItemService;

    private final ItemCatalogService itemCatalogService;

    private final PlayersAmountService playersAmountService;

    @GetMapping("/players")
    public List<String> onlinePlayers() {
        return playersAmountService.getOnlinePlayers();
    }

    @GetMapping("/items")
    public List<String> findItems(@RequestParam("prefix") final String prefix) {
        return itemCatalogService.findByNamePrefix(prefix);
    }

    @PostMapping
    public ResponseEntity<String> giveItem(@RequestBody final GiveItemRequest request) {
        return ResponseEntity.ok(giveItemService.giveItem(request.username(), request.item(), request.count()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidRequest(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public record GiveItemRequest(String username, String item, int count) {
    }
}
