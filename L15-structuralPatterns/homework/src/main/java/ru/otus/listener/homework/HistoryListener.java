package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private static final Map<Long, Message> messages = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messages.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messages.get(id));
    }
}
