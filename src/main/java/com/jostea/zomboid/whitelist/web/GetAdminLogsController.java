package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.service.GetAdminLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get-admin-logs")
public class GetAdminLogsController {

    private final  GetAdminLogsService getAdminLogsService;

    @GetMapping
    @Transactional
    public ResponseEntity<String> adminLogs() {
        return ResponseEntity.ok(getAdminLogsService.getAdminLogs());
    }
}
