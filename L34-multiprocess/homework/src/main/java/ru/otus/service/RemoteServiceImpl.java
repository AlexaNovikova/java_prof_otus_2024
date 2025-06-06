package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.ClientMessage;
import ru.otus.RemoteServiceGrpc;
import ru.otus.ServerMessage;

@SuppressWarnings({"squid:S2142", "squid:S106"})
public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void generate(ClientMessage request, StreamObserver<ServerMessage> responseObserver) {
        var value = request.getFirstValue() - 1;

        while (value <= request.getLastValue()) {
            value++;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            responseObserver.onNext(ServerMessage.newBuilder().setValue(value).build());
        }

        responseObserver.onCompleted();
    }
}
