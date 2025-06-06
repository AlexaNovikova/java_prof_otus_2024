package ru.otus;

import io.grpc.ServerBuilder;
import java.io.IOException;
import ru.otus.service.RemoteServiceImpl;

@SuppressWarnings({"squid:S106"})
public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteService = new RemoteServiceImpl();

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();

        server.start();

        System.out.println("Server waiting for client connections...");
        server.awaitTermination();
    }
}
