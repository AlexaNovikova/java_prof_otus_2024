package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowsTimeExceptions implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowsTimeExceptions(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new TimeException("exception even second");
        }
        return message;
    }
}
