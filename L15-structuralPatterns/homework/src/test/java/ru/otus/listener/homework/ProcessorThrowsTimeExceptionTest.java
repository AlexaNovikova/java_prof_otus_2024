package ru.otus.listener.homework;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorThrowsTimeExceptions;
import ru.otus.processor.homework.TimeException;

public class ProcessorThrowsTimeExceptionTest {

    @Test
    @DisplayName("Тестируем, что исключение выбрасывается на четной секунде")
    void testException() {
        var message = new Message.Builder(1L).field1("field1").field2("field2").build();

        var processorThrowsException = new ProcessorThrowsTimeExceptions(() -> LocalDateTime.of(2025, 12, 1, 1, 2, 2));

        assertThatExceptionOfType(TimeException.class).isThrownBy(() -> processorThrowsException.process(message));
    }
}
