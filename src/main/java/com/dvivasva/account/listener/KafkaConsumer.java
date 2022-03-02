package com.dvivasva.account.listener;

import com.dvivasva.account.component.AccountComponent;
import com.dvivasva.account.model.Account;
import com.dvivasva.account.service.KafkaProducer;
import com.dvivasva.account.utils.JsonUtils;
import com.dvivasva.account.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final AccountComponent accountComponent;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = Topic.INS_ACCOUNT, groupId = "group_id")
    public void consume(String param) {
        logger.info("Has been published card number from service card-kr : " + param);
        createAccount(param);
    }
    public void createAccount(String param) {

        var account = new Account();
        try {
            account = JsonUtils.convertFromJsonToObject(param, Account.class);

            var ins = accountComponent.create(account);
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

    }
    @KafkaListener(topics = Topic.FIND_ACCOUNT_DESTINATION, groupId = "group_id")
    public void consumeFindAccountDestination(String param) {
        logger.info("Has been published ACCOUNT number from service card-kr : " + param);
        //return Account

    }

    public void responseMessageAccount(String param, int index) {
        String newNumberAccount = JsonUtils.removeFirstAndLast(param);
        var find = accountComponent.findByNumberAccount(newNumberAccount);
        find.doOnNext(p -> {

            if (index == 0) {

                kafkaProducer.responseAccountOrigin(p);
            } else {
                kafkaProducer.responseAccountDestination(p);
            }
            logger.info("send messages to account -->");
        }).subscribe();
    }

}
