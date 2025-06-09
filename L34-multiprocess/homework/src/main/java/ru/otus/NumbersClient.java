package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"squid:S106", "squid:S2142"})
public class NumbersClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteServiceGrpc.newStub(channel);
        var generatedNumberFromServer = new AtomicLong();
        var latch = new CountDownLatch(1);

        stub.generate(
                ClientMessage.newBuilder().setFirstValue(1).setLastValue(30).build(), new StreamObserver<>() {
                    @Override
                    public void onNext(ServerMessage value) {
                        log.debug("New value from server: {}", value.getValue());
                        generatedNumberFromServer.set(value.getValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("error {}", t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        log.debug("Completed!");
                        latch.countDown();
                    }
                });

        var currentValue = 0L;

        long newValueFromServer;

        for (int i = 0; i <= 50; i++) {
            newValueFromServer = generatedNumberFromServer.getAndSet(0L);
            currentValue = currentValue + newValueFromServer + 1;
            log.debug("Current value: {}", currentValue);
            Thread.sleep(1000);
        }

        latch.await();
        channel.shutdown();
    }
}
