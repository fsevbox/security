package org.techfrog.springjwt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity ping() {
        return new ResponseEntity("Secured ping", HttpStatus.OK);
    }
}
