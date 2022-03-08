package com.dvivasva.account.message;

import com.dvivasva.account.model.Payment;
import com.dvivasva.account.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final KafkaTemplate<String, Payment> requestPaymentKafkaTemplate;

    public void sendRequestPaymentToCard(Payment payment) {
        requestPaymentKafkaTemplate.send(Topic.RESPONSE_PAYMENT_ON_CARD,payment);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_PAYMENT_ON_CARD);

    }
}
