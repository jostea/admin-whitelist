package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import com.jostea.zomboid.whitelist.service.PlayersWhitelistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/players-whitelist")
public class AllowedPlayerController {

    @Autowired
    private HttpServletRequest request;

    private final AllowedPlayerRepository allowedPlayerRepository;
    private final PlayersWhitelistService playersWhitelistService;

    @ModelAttribute
    public AllowedPlayer allowedPlayer() {
        String ipAddress = request.getRemoteAddr();
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

//        allowedPlayerRepository.save(allowedPlayer);
        playersWhitelistService.savePlayer(request.getRemoteAddr(), allowedPlayer);
        return "redirect:/players-whitelist";
    }

    @GetMapping("/delete/{username}")
    @Transactional
    public String deletePlayer(@PathVariable("username") final String username) {
//        allowedPlayerRepository.deleteByUsername(username);
        playersWhitelistService.deletePlayer(request.getRemoteAddr(), username);
        return "redirect:/players-whitelist";
    }
}
