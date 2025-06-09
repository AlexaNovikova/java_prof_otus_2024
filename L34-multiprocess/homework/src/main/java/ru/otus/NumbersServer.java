package ru.otus;

import io.grpc.ServerBuilder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.RemoteServiceImpl;

@SuppressWarnings({"squid:S106"})
public class NumbersServer {

    public static final int SERVER_PORT = 8190;
    private static final Logger log = LoggerFactory.getLogger(NumbersServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteService = new RemoteServiceImpl();

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();

        server.start();

        log.debug("Server waiting for client connections...");
        server.awaitTermination();
    }
}
