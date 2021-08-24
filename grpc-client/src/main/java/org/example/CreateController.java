package org.example;

import com.example.grpc.service.Book;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.example.util.AlertUtil;
import org.example.util.FXMLUtil;

import java.util.Optional;

public class CreateController extends Controller {

    @FXML
    private TextField bookName;

    @FXML
    private TextField author;

    @FXML
    private TextField publisher;

    @FXML
    private Button create;

    @FXML
    private Button delete;

    @FXML
    private Label notifyMsg;

    public CreateController() {
        FXMLUtil.loadFXML("create", this);
        tableView.setItems(FXCollections.observableArrayList(getAllBooks()));
        create.setOnAction(e -> addBook(bookName.getText(), author.getText(), publisher.getText()));
        delete.setOnAction(event -> handleDeleteBook());
    }

    /**
     * Create book
     *
     * @param bookName
     * @param author
     * @param publisher
     */
    public void addBook(String bookName, String author, String publisher) {
        if (!validate(bookName, author)) {
            return;
        }
        getBooks().add(blockingStub.create(Book.newBuilder()
                .setBookName(bookName)
                .setAuthor(author)
                .setPublisher(publisher)
                .build()));
        AlertUtil.showNotify(notifyMsg, Color.GREEN, "Create Success!");
        clean();
    }

    /**
     * Handle delete book  <br>
     * Show message when delete success or not
     */
    public void handleDeleteBook() {
        Book book = tableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            AlertUtil.showWarnAlert("Please select book to delete!");
            return;
        }
        Alert alert = AlertUtil.showConfirmationAlert("Are you sure want to delete ?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get() == ButtonType.OK) {
                deleteBook(book);
                AlertUtil.showNotify(notifyMsg, Color.GREEN, "Book Deleted!");
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            AlertUtil.showNotify(notifyMsg, Color.RED, "Delete failed!");
        }
    }

    /**
     * Delete book
     *
     * @param book
     */
    public void deleteBook(Book book) {
        blockingStub.delete(Int64Value.of(book.getId()));
        tableView.setItems(FXCollections.observableArrayList(blockingStub
                .findBookList(Empty.getDefaultInstance())
                .getBookListList()));
        tableView.refresh();
    }

    /**
     * Clean all text field after add success
     */
    public void clean() {
        bookName.clear();
        author.clear();
        publisher.clear();
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
