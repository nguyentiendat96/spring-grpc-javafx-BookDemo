package com.example.grpcserver.repository;

import com.example.grpc.entites.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByOrderByBookNameAsc();
    List<BookEntity> findByBookNameAndAuthor(String bookName, String author);
    List<BookEntity> findByBookNameContainsOrAuthorContains(String bookName, String author);
}
