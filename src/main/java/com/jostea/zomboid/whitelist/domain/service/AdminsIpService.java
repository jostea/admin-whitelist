package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.domain.model.AdminsIP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminsIpService {

    private final WhitelistProperties whitelistProperties;

    public String saveAdminsIp(String superAdminIp, String adminIp) {
        if (whitelistProperties.getSuperAdminIps().contains(superAdminIp)) {
            AdminsIP adminsIP = new AdminsIP();
            adminsIP.setIp(adminIp);
            return "IP added";
        }
        return "You don't have permission to add IP";
    }

    public String removeAdminsIp(String superAdminIp, String adminIp) {
        if (whitelistProperties.getSuperAdminIps().contains(superAdminIp)) {
            return "IP removed";
        }
        return "You don't have permission to remove IP";
    }
}
