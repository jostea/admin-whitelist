package com.jostea.zomboid.whitelist.web;

import com.jostea.zomboid.whitelist.domain.service.GetAdminLogsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class GetAdminLogsController {

    private final GetAdminLogsService getAdminLogsService;

    @GetMapping("/get-admin-logs")
    public ResponseEntity<String> adminLogs() {
        return ResponseEntity.ok(getAdminLogsService.getAdminLogs());
    }

    @GetMapping("/get-debug-log-server")
    public ResponseEntity<String> getDebugLogs(HttpServletResponse httpServletResponse) throws IOException {
        IOUtils.copy(getAdminLogsService.getDebugLogServer(), httpServletResponse.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
