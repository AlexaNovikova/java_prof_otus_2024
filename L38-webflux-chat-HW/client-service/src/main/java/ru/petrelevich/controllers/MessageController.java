package ru.petrelevich.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.petrelevich.domain.Message;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String TOPIC_TEMPLATE = "/topic/response.";

    private static final String ROOM_1408 = "1408";

    private final WebClient datastoreClient;
    private final SimpMessagingTemplate template;

    public MessageController(WebClient datastoreClient, SimpMessagingTemplate template) {
        this.datastoreClient = datastoreClient;
        this.template = template;
    }

    @MessageMapping("/message.{roomId}")
    public void getMessage(@DestinationVariable("roomId") String roomId, Message message) {
        logger.info("get message:{}, roomId:{}", message, roomId);
        if (!roomId.equals(ROOM_1408)) {
            saveMessage(roomId, message).subscribe(msgId -> logger.info("message send id:{}", msgId));

            template.convertAndSend(
                    String.format("%s%s", TOPIC_TEMPLATE, roomId),
                    new Message(HtmlUtils.htmlEscape(message.messageStr())));
            template.convertAndSend(
                    String.format("%s%s", TOPIC_TEMPLATE, ROOM_1408),
                    new Message(HtmlUtils.htmlEscape(message.messageStr())));
        } else logger.info("It is impossible to send messages from room :{}", roomId);
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var genericMessage = (GenericMessage<byte[]>) event.getMessage();
        var simpDestination = (String) genericMessage.getHeaders().get("simpDestination");
        if (simpDestination == null) {
            logger.error("Can not get simpDestination header, headers:{}", genericMessage.getHeaders());
            throw new ChatException("Can not get simpDestination header");
        }
        if (!simpDestination.startsWith(TOPIC_TEMPLATE)) {
            return;
        }
        var roomId = parseRoomId(simpDestination);

        var principal = event.getUser();
        if (principal == null) {
            return;
        }
        logger.info("subscription for:{}, roomId:{}, user:{}", simpDestination, roomId, principal.getName());
        // /user/f6532733-51db-4d0e-bd00-1267dddc7b21/topic/response.1

        if (roomId == Long.parseLong(ROOM_1408)) {
            getAllMessages()
                    .doOnError(ex -> logger.error("getting messages failed", ex))
                    .subscribe(message -> template.convertAndSendToUser(principal.getName(), simpDestination, message));
        } else {
            getMessagesByRoomId(roomId)
                    .doOnError(ex -> logger.error("getting messages for roomId:{} failed", roomId, ex))
                    .subscribe(message -> template.convertAndSendToUser(principal.getName(), simpDestination, message));
        }
    }

    private long parseRoomId(String simpDestination) {
        try {
            var idxRoom = simpDestination.lastIndexOf(TOPIC_TEMPLATE);
            return Long.parseLong(simpDestination.substring(idxRoom).replace(TOPIC_TEMPLATE, ""));
        } catch (Exception ex) {
            logger.error("Can not get roomId", ex);
            throw new ChatException("Can not get roomId");
        }
    }

    private Mono<Long> saveMessage(String roomId, Message message) {
        return datastoreClient
                .post()
                .uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .exchangeToMono(response -> response.bodyToMono(Long.class));
    }

    private Flux<Message> getMessagesByRoomId(long roomId) {
        return datastoreClient
                .get()
                .uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(Message.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }

    private Flux<Message> getAllMessages() {
        return datastoreClient
                .get()
                .uri("/msg")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(Message.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }
}
