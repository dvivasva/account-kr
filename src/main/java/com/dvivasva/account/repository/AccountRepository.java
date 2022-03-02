package com.dvivasva.account.repository;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.utils.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepository {


    private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);
    private static final String KEY = "Account";
    private final ReactiveRedisOperations<String, Account> redisOperations;
    private final ReactiveHashOperations<String, String, Account> hashOperations;


    @Autowired
    public AccountRepository(ReactiveRedisOperations<String, Account> redisOperations) {
        this.redisOperations = redisOperations;
        this.hashOperations = redisOperations.opsForHash();
    }

    public Mono<AccountDto> create(Account account) {
        logger.info("inside methode create");
        if (account.getId() != null) {
            String id = UUID.randomUUID().toString();
            account.setId(id);
        }
        return hashOperations.put(KEY, account.getId(), account)
                .map(isSaved -> account).map(AccountUtil::entityToDto);
    }

    public Flux<AccountDto> read() {
        return hashOperations.values(KEY).map(AccountUtil::entityToDto);
    }

    public Mono<AccountDto> update(Account account, String id) {
        Mono<Boolean> booleanMono = existsById(id);
        return booleanMono.flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)) {
                        return hashOperations.put(KEY, account.getId(), account)
                                .map(isSaved -> account);
                       /* return Mono.error(new DuplicateKeyException("Duplicate key, numberCard: " +
                                account.getNumberCard() + " or numberPhone: " + account.getNumberPhone() + " exists."));*/
                    } else {
                        return hashOperations.put(KEY, account.getId(), account)
                                .map(isSaved -> account);
                    }
                })
                .thenReturn(account).map(AccountUtil::entityToDto);
    }


    public Mono<Boolean> existsById(String id) {
        return hashOperations.hasKey(KEY, id);
    }

    public Mono<Void> delete(String id) {
        return hashOperations.remove(KEY, id).then();
    }

    public Mono<AccountDto> findById(String id) {
        return hashOperations.get(KEY, id).map(AccountUtil::entityToDto);
    }

    public Mono<AccountDto> findByNumberAccount(String numberAccount) {
        return hashOperations.values(KEY)
                .filter(p -> p.getNumber().equals(numberAccount))
                .singleOrEmpty().map(AccountUtil::entityToDto);
    }


}
