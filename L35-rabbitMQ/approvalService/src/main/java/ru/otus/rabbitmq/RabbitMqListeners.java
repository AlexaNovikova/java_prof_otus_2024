package ru.otus.rabbitmq;

import com.rabbitmq.client.Channel;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.otus.domain.Client;
import ru.otus.domain.VerificationStatus;
import ru.otus.services.RabbitMqService;

@Slf4j
@Component
@SuppressWarnings({"java:S112", "java:S125", "java:S2245"})
public class RabbitMqListeners {

    private final RabbitMqService rabbitMqService;
    private final Random random = new Random();

    public RabbitMqListeners(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    @RabbitListener(queues = "new-clients-queue", ackMode = "AUTO")
    public void newClientsEventsQueueListener(
            Client client, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        client.setVerificationStatus(getRandomStatus());
        TimeUnit.SECONDS.sleep(10);
        // channel.basicAck(tag, false);
        rabbitMqService.sendClientApprovedEvent(client);

        // Для ackMode = "AUTO" и перехода в dead letter exchange
        // throw new AmqpRejectAndDontRequeueException("Ooops");
    }

    @RabbitListener(queues = "new-clients-queue2", ackMode = "AUTO")
    public void newClientsEventsQueueListener2(Message message) throws InterruptedException {
        log.info("В new-clients-queue2 было получено сообщение: {}", message);
        TimeUnit.SECONDS.sleep(10);
    }

    @RabbitListener(queues = "all-clients-events-queue", ackMode = "AUTO")
    public void allClientsEventsQueueListener(Message message) throws InterruptedException {
        log.info("В all-clients-events-queue было получено сообщение: {}", message);
        TimeUnit.SECONDS.sleep(10);
    }

    @RabbitListener(queues = "dead-letter-queue")
    public void deadLetterQueueListener(Message message) {
        log.info("Было получено dead-сообщение: {}", message);
    }

    private VerificationStatus getRandomStatus() {
        return random.nextBoolean() ? VerificationStatus.VERIFIED : VerificationStatus.REJECTED;
    }

    @RabbitListener(queues = "new-clients-rpc-queue")
    public Client newClientsEventsRpcQueueListener(Client client) throws Exception {
        client.setVerificationStatus(getRandomStatus());
        TimeUnit.SECONDS.sleep(10);
        return client;
    }
}
