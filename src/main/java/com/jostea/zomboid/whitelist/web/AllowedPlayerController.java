package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/allowed-player")
public class AllowedPlayerController {

    private final AllowedPlayerRepository allowedPlayerRepository;

    @ModelAttribute
    public AllowedPlayer allowedPlayer(){
        return new AllowedPlayer();
    }

    @GetMapping
    public String mainPage(final Model model) {
        model.addAttribute("players", allowedPlayerRepository.findAll());
        return "index";
    }

    @PostMapping
    @Transactional
    public String savePlayer(@Valid final AllowedPlayer allowedPlayer, final BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }

        allowedPlayerRepository.save(allowedPlayer);
        return "redirect:/allowed-player";
    }

    @GetMapping("/delete/{username}")
    @Transactional
    public String deletePlayer(@PathVariable("username") final String username) {
        allowedPlayerRepository.deleteByUsername(username);
        return "redirect:/allowed-player";
    }
}
