package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.AdminsIpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins-ip")
public class AdminsIpController {

    private final AdminsIpService adminsIpService;

    @Transactional
    @GetMapping("add-ip/{ip}")
    public String saveIp(HttpServletRequest request, @PathVariable("ip") final String adminIp) {
        return adminsIpService.saveAdminsIp(request.getRemoteAddr(), adminIp);
    }

    @Transactional
    @GetMapping("delete-ip/{ip}")
    public String deleteIp(HttpServletRequest request, @PathVariable("ip") final String adminIp) {
        return adminsIpService.removeAdminsIp(request.getRemoteAddr(), adminIp);
    }
}
