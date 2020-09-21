package org.techfrog.mutualtls.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techfrog.mutualtls.server.aspect.LogCertificate;

@RestController
public class HealthController {

    @LogCertificate(detailed = true)
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
