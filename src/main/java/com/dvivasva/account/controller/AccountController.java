package com.dvivasva.account.controller;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    /**
     * @param accountDtoMono .
     * @return status 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDto> create(
            @RequestBody final Mono<AccountDto> accountDtoMono) {
        return accountService.create(accountDtoMono);
    }
    /**
     * @return flux .
     */
    @GetMapping
    public Flux<AccountDto> read() {
        return accountService.read();
    }

}
