package com.dvivasva.account.component;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AccountComponent {

    private final AccountRepository accountRepository;

    public Mono<AccountDto> create(Account account) {
        return accountRepository.create(account);
    }

    public Flux<AccountDto> read() {
        return accountRepository.read();
    }

    public Mono<AccountDto> update(Account account, String id) {
        return accountRepository.update(account, id);
    }

    public Mono<Void> delete(String id) {
        return accountRepository.delete(id);
    }

    public Mono<AccountDto> findById(String id) {
        return accountRepository.findById(id);
    }

    public Mono<AccountDto> findByNumberAccount(String numberAccount) {
        return accountRepository.findByNumberAccount(numberAccount);
    }



}
