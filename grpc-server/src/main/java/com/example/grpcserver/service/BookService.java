package com.example.grpcserver.service;

import com.example.grpc.entites.BookEntity;
import com.example.grpc.service.Book;
import com.example.grpc.service.Search;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import java.util.List;

public interface BookService {
    List<Book> findBookList(Empty request);

    BookEntity create(Book request);

    void delete(Int64Value request);

    List<Book> search(Search request);

    List<Book> searchAll(Search request);
}
