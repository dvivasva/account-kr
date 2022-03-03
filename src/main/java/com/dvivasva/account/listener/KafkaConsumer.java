package com.dvivasva.account.listener;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.service.AccountService;
import com.dvivasva.account.service.KafkaProducer;
import com.dvivasva.account.utils.JsonUtils;
import com.dvivasva.account.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final AccountService accountService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = Topic.INS_ACCOUNT, groupId = "group_id")
    public void consume(String param) {
        logger.info("Has been published card number from service card-kr : " + param);
        createAccount(param);
    }
    public void createAccount(String param) {

        var accountDto = new AccountDto();
        try {
            accountDto = JsonUtils.convertFromJsonToObject(param, AccountDto.class);

            var ins = accountService.create(Mono.just(accountDto));
            ins.doOnNext(p -> logger.info("registry success" + p))
                    .subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = Topic.FIND_ACCOUNT_ORIGIN, groupId = "group_id")
    public void consumeFindAccountOrigin(String param) {
        logger.info("Has been published ACCOUNT number from service card-kr : " + param);
        //return Account
        responseMessageAccount(param,0);
        logger.info("send details account origin to payment -->");

    }
    @KafkaListener(topics = Topic.FIND_ACCOUNT_DESTINATION, groupId = "group_id")
    public void consumeFindAccountDestination(String param) {
        logger.info("Has been published ACCOUNT number from service card-kr : " + param);

       responseMessageAccount(param,1);
        logger.info("send details account destination to payment -->");

    }

    public void responseMessageAccount(String param, int index) {
        String newNumberAccount = JsonUtils.removeFirstAndLast(param);

        var find = accountService.findByNumberAccount(newNumberAccount);
        find.doOnNext(p -> {

            if (index == 0) {

                kafkaProducer.responseAccountOrigin(p);
            } else {
                kafkaProducer.responseAccountDestination(p);
            }

        }).subscribe();
    }


    @KafkaListener(topics = Topic.UPD_ACCOUNT_ORIGIN, groupId = "group_id")
    public void consumeUpdOrigin(String param) {
        logger.info("Has been published ACCOUNT number from service payment-kr : " + param);


    }
    @KafkaListener(topics = Topic.UPD_ACCOUNT_DESTINATION, groupId = "group_id")
    public void consumeUpdDestination(String param) {
        logger.info("Has been published ACCOUNT number from service payment-kr : " + param);

    }
}
