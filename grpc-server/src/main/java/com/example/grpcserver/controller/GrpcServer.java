package com.example.grpcserver.controller;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

//@SpringBootApplication
public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new BookController())
                .addService(new HelloServiceImpl())
                .build();
        System.out.println("server starting ...");
        server.start();
        server.awaitTermination();
    }
}
