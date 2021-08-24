package com.example.grpcserver.service;

import com.example.grpc.entites.BookEntity;
import com.example.grpc.service.Book;
import com.example.grpc.service.Search;
import com.example.grpcserver.repository.BookRepository;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findBookList(Empty request) {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream().map(BookEntity::toProto).collect(Collectors.toList());
    }

    @Override
    public BookEntity create(Book request) {
        return bookRepository.save(new BookEntity(request));
    }

    @Override
    public void delete(Int64Value request) {
        bookRepository.deleteById(request.getValue());
    }

    @Override
    public List<Book> search(Search request) {
        List<BookEntity> searchEntities = bookRepository.findByBookNameContainsOrAuthorContains(request.getBookName(), request.getBookName());
        return searchEntities.stream().map(BookEntity::toProto).collect(Collectors.toList());
    }

    @Override
    public List<Book> searchAll(Search request) {
        List<BookEntity> bookEntities = bookRepository.findAllByOrderByBookNameAsc();
        return bookEntities.stream().map(BookEntity::toProto).collect(Collectors.toList());
    }
}
