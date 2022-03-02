package com.dvivasva.account.utils;

import com.dvivasva.account.component.AccountComponent;
import com.dvivasva.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultLoad implements CommandLineRunner {

    private final AccountComponent accountComponent;

    @Override
    public void run(String... args) throws Exception {
        var accountOne = new Account("account-001",  "018-000-101",1200.85);
        accountComponent.create(accountOne);
        var accountTwo = new Account("account-002",  "018-000-102",2350.55);
        accountComponent.create(accountTwo);
        var accountThree = new Account("account-003", "018-000-103",1500.60);
        accountComponent.create(accountThree);
        var accountFour = new Account("account-004","018-000-104",600.50);
        accountComponent.create(accountFour);
        var accountFive = new Account("account-005",  "018-000-105",1200.40);
        accountComponent.create(accountFive);
        var accountSix = new Account("account-006",  "018-000-106",1600.80);
        accountComponent.create(accountSix);
        var accountSeven = new Account("account-007", "018-000-107",2200.30);
        accountComponent.create(accountSeven);
        var accountEight = new Account("account-008",  "018-000-108",4600.45);
        accountComponent.create(accountEight);
        var accountNine = new Account("account-009",  "018-000-109",900.20);
        accountComponent.create(accountNine);
        var accountTen = new Account("account-010",  "018-000-110",1100.80);
        accountComponent.create(accountTen);
    }
}
