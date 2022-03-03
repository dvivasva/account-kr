package com.dvivasva.account.service;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.repository.IAccountRepository;
import com.dvivasva.account.utils.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private  final IAccountRepository iAccountRepository;


    public Mono<AccountDto> create(final Mono<AccountDto> entityToDto) {
        return entityToDto.map(AccountUtil::dtoToEntity)
                .flatMap(iAccountRepository::save)
                .map(AccountUtil::entityToDto);

    }
    public Flux<AccountDto> read() {
        return iAccountRepository.findAll().map(AccountUtil::entityToDto);
    }

    public Mono<AccountDto> findByNumberAccount(String number) {
        logger.info("inside methode find by account ");
        Query query = new Query();
        query.addCriteria(Criteria.where("number").is(number));
        return reactiveMongoTemplate.findOne(query, Account.class).map(AccountUtil::entityToDto);

    }
}
