package com.jazzchris.currencyexchange.controller;

import com.jazzchris.currencyexchange.entity.TransactionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/future")
public class FutureTransactionRestController {

    @PostMapping
    public ResponseEntity<TransactionDetails> futureTransactionRequest(@RequestBody TransactionDetails details, Authentication auth) {
        System.err.println(details);
        return ResponseEntity.ok().body(details);
    }

    @GetMapping
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok().body("Hello!");
    }

}
