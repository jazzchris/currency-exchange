package com.jazzchris.currencyexchange.controller;

import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.service.FutureOrderService;
import com.jazzchris.currencyexchange.service.UserService;
import com.jazzchris.currencyexchange.stock.Currency;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("FutureControllerV1")
@RequestMapping("/future")
@Api(value="Future")
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

    @GetMapping(
            value = "/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Read info about the customer",
            nickname = "getUsers v1",
            response = Users.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found the customer"),
            @ApiResponse(code = 400, message = "No customer found"),
            @ApiResponse(code = 500, message = "Error occurs during finding the customer")
    })
    public Users getUsers(@PathVariable("username") String username) {
        Users user = userService.findByUsername(username).orElse(null);
        return user;
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("username") String username, @PathVariable("id") int id) {
        Users user = userService.findByUsername(username).get();
        Optional<FutureOrder> order = futureOrderService.findById(id);
        System.out.println(order.get());
        return ResponseEntity.ok().body(order.get());
    }
}
