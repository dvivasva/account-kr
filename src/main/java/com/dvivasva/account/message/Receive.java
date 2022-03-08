package com.dvivasva.account.message;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Payment;
import com.dvivasva.account.service.AccountService;
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
public class Receive {

    private static final Logger logger = LoggerFactory.getLogger(Receive.class);
    private final AccountService accountService;
    private final Sender sender;

    @KafkaListener(topics = Topic.FIND_NUMBERS_ACCOUNTS, groupId = "group_id_account")
    public void consumeFromCard(String param) {
        logger.info("Has been published an insert payment from service card-rk : " + param);
        sendMessageToCard(param);
    }

    public void sendMessageToCard(String param) {
        Payment payment;
        try {
            payment = JsonUtils.convertFromJsonToObject(param, Payment.class);
            Mono<AccountDto> accountOrigin = accountService.findByNumberAccount(payment.getNumberPhoneOrigin());
            accountOrigin
                    .switchIfEmpty(Mono.error(new ClassNotFoundException("not exist account")))
                    .doOnNext(p -> {

                        payment.setNumberPhoneOrigin(p.getNumber());
                        p.setAvailableBalance(p.getAvailableBalance()- payment.getAmount());

                        var updOrigin=accountService.update(Mono.just(p),p.getId());
                        updOrigin.doOnNext(origin->{
                            logger.info("update available account origin --> "+origin);
                        }).subscribe();


                        Mono<AccountDto> accountDestination = accountService.findByNumberAccount(payment.getNumberPhoneDestination());
                        accountDestination.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist account")))
                                .doOnNext(v -> {
                                    payment.setNumberPhoneDestination(v.getNumber());
                                    v.setAvailableBalance(v.getAvailableBalance()+payment.getAmount());

                                    var updDestination=accountService.update(Mono.just(v),v.getId());
                                    updDestination.doOnNext(destination->{

                                        logger.info("update available account destination --> "+destination);

                                    }).subscribe();

                                    sender.sendRequestPaymentToCard(payment);
                                    logger.info("send messages to card-rk -->");
                                }).subscribe();
                    }).subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
