package com.example.grpc.entites;
import com.example.grpc.service.Book;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class BookEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Book toProto() {
        return Book.newBuilder()
                .setId(getId())
                .setBookName(getBookName())
                .setAuthor(getAuthor())
                .setPublisher(getPublisher())
                .build();
    }

    public BookEntity(Book book) {
        this.bookName = book.getAuthor();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
    }

    public BookEntity() {
    }
}
