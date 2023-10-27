package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.repository.domain.model.AdminsIP;
import com.jostea.zomboid.whitelist.repository.extension.AdminsIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminsIpService {

    private final AdminsIpRepository adminsIpRepository;

    private final WhitelistProperties whitelistProperties;

    public String saveAdminsIp(String superAdminIp, String adminIp) {

        if (whitelistProperties.getSuperAdminIps().contains(superAdminIp)) {
            AdminsIP adminsIP = new AdminsIP();
            adminsIP.setIp(adminIp);
            adminsIpRepository.save(adminsIP);
            return "Ip added";
        }
        return "You don't have permission to add ip";
    }

    public String removeAdminsIp(String superAdminIp, String adminIp) {

        if (whitelistProperties.getSuperAdminIps().contains(superAdminIp)) {
            adminsIpRepository.deleteByIp(adminIp);
            return "Ip removed";
        }
        return "You don't have permission to remove ip";
    }
}
