package com.dvivasva.account.controller;

import com.dvivasva.account.component.AccountComponent;
import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountComponent accountComponent;

    /**
     * @param account .
     * @return status 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDto> create(
            @RequestBody final Account account) {
        return accountComponent.create(account);
    }
    /**
     * @return flux .
     */
    @GetMapping
    public Flux<AccountDto> read() {
        return accountComponent.read();
    }

}
