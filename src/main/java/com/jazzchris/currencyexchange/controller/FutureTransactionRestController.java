package com.jazzchris.currencyexchange.controller;

import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.service.FutureOrderService;
import com.jazzchris.currencyexchange.service.UserService;
import com.jazzchris.currencyexchange.stock.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/future")
public class FutureTransactionRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private FutureOrderService futureOrderService;

    @PostMapping
    public ResponseEntity<TransactionDetails> futureTransactionRequest(@RequestBody TransactionDetails details, Authentication auth) {
        Users user = userService.findByUsername(auth.getName()).get();
        futureOrderService.save(user, details);
        return ResponseEntity.ok().body(details);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Users> getUsers(@PathVariable("username") String username) {
        Users user = userService.findByUsername(username).get();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("username") String username, @PathVariable("id") int id) {
        Users user = userService.findByUsername("john").get();
        Optional<FutureOrder> order = futureOrderService.findById(id);
        System.out.println(order.get());
        return ResponseEntity.ok().body(order.get());
    }

    @GetMapping("/testInsert")
    public ResponseEntity<String> getTest() throws InterruptedException {
        Users user = userService.findByUsername("john").get();
        TransactionDetails details = new TransactionDetails();
        details.setCurrency(Currency.EUR);
        details.setTransactionType(TransactionType.BUY);
        details.setTransUnits(20);
        details.setUnitPrice(BigDecimal.valueOf(4.335));
        futureOrderService.save(user, details);
        return ResponseEntity.ok().body("Done!");
    }
}
