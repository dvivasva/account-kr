package com.dvivasva.account.utils;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DefaultLoad implements CommandLineRunner {

    private final AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        var accountOne = new AccountDto("account-001",  "018-000-101",1200.85);
        accountService.create(Mono.just(accountOne));
        var accountTwo = new AccountDto("account-002",  "018-000-102",2350.55);
        accountService.create(Mono.just(accountTwo));
        var accountThree = new AccountDto("account-003", "018-000-103",1500.60);
        accountService.create(Mono.just(accountThree));
        var accountFour = new AccountDto("account-004","018-000-104",600.50);
        accountService.create(Mono.just(accountFour));
        var accountFive = new AccountDto("account-005",  "018-000-105",1200.40);
        accountService.create(Mono.just(accountFive));
        var accountSix = new AccountDto("account-006",  "018-000-106",1600.80);
        accountService.create(Mono.just(accountSix));
        var accountSeven = new AccountDto("account-007", "018-000-107",2200.30);
        accountService.create(Mono.just(accountSeven));
        var accountEight = new AccountDto("account-008",  "018-000-108",4600.45);
        accountService.create(Mono.just(accountEight));
        var accountNine = new AccountDto("account-009",  "018-000-109",900.20);
        accountService.create(Mono.just(accountNine));
        var accountTen = new AccountDto("account-010",  "018-000-110",1100.80);
        accountService.create(Mono.just(accountTen));
    }
}
