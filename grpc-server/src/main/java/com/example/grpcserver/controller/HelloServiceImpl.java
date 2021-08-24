package com.example.grpcserver.controller;

import com.example.grpc.HelloRequest;
import com.example.grpc.HelloResponse;
import com.example.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = new StringBuilder()
                .append("Hello ! ")
                .append(request.getName())
                .append(" ")
                .append(request.getAge())
                .append(". Hobbies: ")
                .append(request.getHobbies(0))
                .toString();

        HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();
        System.out.println(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
