package org.example;

import com.example.grpc.service.Book;
import com.example.grpc.service.BookServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.example.util.AlertUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Controller extends BorderPane {
    @FXML
    public TableView<Book> tableView;
    public static ManagedChannel channel;
    public static BookServiceGrpc.BookServiceBlockingStub blockingStub;
    public static List<Book> books;

    public Controller() {
        initBlockingStub();
    }

    public static BookServiceGrpc.BookServiceBlockingStub initBlockingStub() {
        if (channel != null) {
            return blockingStub;
        }
        System.out.println("Start new channel: ");
        channel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        blockingStub = BookServiceGrpc.newBlockingStub(channel);
        return blockingStub;
    }

    public static List<Book> getAllBooks() {
        if (books == null) {
            books = blockingStub.findBookList(Empty.getDefaultInstance()).getBookListList();
            return books;
        }
        return books;
    }

    public ObservableList<Book> getBooks() {
        return tableView.getItems();
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * validate input
     *
     * @param bookName
     * @param author
     * @return boolean
     */
    private boolean validate(String bookName, String author) {
        StringBuilder errors = new StringBuilder();
        if (bookName.trim().isEmpty()) {
            errors.append("- Please enter a book name.\n");
        }
        if (author.trim().isEmpty()) {
            errors.append("- Please enter a author.\n");
        }
        if (errors.length() > 0) {
            AlertUtil.showWarnAlert(errors.toString());
            return false;
        }
        return true;
    }
}
