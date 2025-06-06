package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings({"squid:S106", "squid:S2142"})
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteServiceGrpc.newStub(channel);
        var generatedValue = new AtomicLong();
        var latch = new CountDownLatch(1);

        stub.generate(
                ClientMessage.newBuilder().setFirstValue(0).setLastValue(30).build(), new StreamObserver<>() {
                    @Override
                    public void onNext(ServerMessage value) {
                        System.out.println("Get value from server: " + value.getValue());
                        generatedValue.set(value.getValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("error " + t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("\nCompleted!");
                        latch.countDown();
                    }
                });

        var currentValue = 0L;

        for (int i = 0; i <= 50; i++) {
            var value = generatedValue.getAndSet(0);
            currentValue = currentValue + value + 1;
            System.out.println("Current value: " + currentValue);

            Thread.sleep(1000);
        }

        latch.await();
        channel.shutdown();
    }
}
