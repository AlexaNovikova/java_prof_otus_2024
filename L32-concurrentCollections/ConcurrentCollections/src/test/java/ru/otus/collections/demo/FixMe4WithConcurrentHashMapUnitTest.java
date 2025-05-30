package ru.otus.collections.demo;

import static java.lang.System.out;
import static org.apache.commons.lang3.RandomStringUtils.secure;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO please FIXME with ConcurrentHashMap
// Вопросы:
// - Какую коллекцию будем менять и на какую?
// - Фиксим тест сейчас!
// - *Для какого сценария по нагрузке больше всего подходит ConcurrentHashMap?
@SuppressWarnings({"java:S1134", "java:S1135"})
class FixMe4WithConcurrentHashMapUnitTest {
    private static final Logger log = LoggerFactory.getLogger(FixMe4WithConcurrentHashMapUnitTest.class);

    private static final int ITERATIONS_COUNT = 10000;

    @Test
    //    @Disabled("Удалить перед исправлением")
    void testConcurrentHashMapWorksGreat() throws InterruptedException {

        final Map<String, String> map = new HashMap<>();
        final CountDownLatch latch = new CountDownLatch(2);
        List<Exception> exceptions = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    log.info("starting adding email {}", i);
                    String s = secure().nextAlphabetic(10) + "@gmail.com";
                    map.put(s, s);
                    log.info("finishing adding email {}", i);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    log.info("starting read iteration {}", i);
                    map.forEach((k, v) -> out.println(k));
                    log.info("finishing read iteration {}", i);
                }
            } catch (Exception ex) {
                exceptions.add(ex);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        assertThat(exceptions).withFailMessage(exceptions.toString()).isEmpty();
    }
}
