package com.example.grpcserver.controller;

import com.example.grpc.entites.BookEntity;
import com.example.grpc.service.Book;
import com.example.grpc.service.BookList;
import com.example.grpc.service.BookServiceGrpc;
import com.example.grpc.service.Search;
import com.example.grpcserver.service.BookService;
import com.example.grpcserver.validation.BookValidator;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GRpcService
public class BookController extends BookServiceGrpc.BookServiceImplBase {

    @Autowired
    BookService bookService;

    @Override
    public void findBookList(Empty request, StreamObserver<BookList> responseObserver) {
        List<Book> bookProtoList = bookService.findBookList(request);
        responseObserver.onNext(BookList.newBuilder().addAllBookList(bookProtoList).build());
        responseObserver.onCompleted();
    }

    @Override
    public void create(Book request, StreamObserver<Book> responseObserver) {
        BookEntity bookEntity = bookService.create(request);
        responseObserver.onNext(bookEntity.toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Int64Value request, StreamObserver<Empty> responseObserver) {
        bookService.delete(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void search(Search request, StreamObserver<BookList> responseObserver) {
        try {
            List<Book> bookProtoList = bookService.search(request);
            responseObserver.onNext(BookList.newBuilder().addAllBookList(bookProtoList).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL));
        }
    }

    @Override
    public void searchAll(Empty request, StreamObserver<BookList> responseObserver) {
        List<Book> bookProtoList = bookService.findBookList(request);
        responseObserver.onNext(BookList.newBuilder().addAllBookList(bookProtoList).build());
        responseObserver.onCompleted();
    }
}
