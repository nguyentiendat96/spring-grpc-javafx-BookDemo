package org.example;

import com.example.grpc.service.Book;
import com.example.grpc.service.BookList;
import com.example.grpc.service.Search;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.example.util.AlertUtil;
import org.example.util.FXMLUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchController extends Controller {

    @FXML
    private TextField bookName;

    @FXML
    private TextField author;

    @FXML
    private Label errorMsg;

    @FXML
    private Button search;

    @FXML
    private Button searchAll;

    public SearchController() {
        FXMLUtil.loadFXML("search", this);
        search.setOnAction(event -> loadBooksByFilter());
        searchAll.setOnAction(event -> loadBooks());
    }

    /**
     * Load books with filter
     */
    private void loadBooksByFilter() {
        try {
            if (!validate(bookName.getText())) {
                return;
            }
            ObservableList<Book> booksFilter = filterList(bookName.getText(), author.getText());
            if (booksFilter.size() == 0) {
                AlertUtil.showNotify(errorMsg, Color.RED, "Can't find the book you need!");
            } else {
                AlertUtil.showNotify(errorMsg, null, null);
            }
            tableView.setItems(booksFilter);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INTERNAL) {
                AlertUtil.showNotify(errorMsg, Color.RED, "Can't find the book you need!");
                tableView.refresh();
            }
        }
    }

    /**
     * Load books without filter
     */
    private void loadBooks() {
        clean();
        List<Book> books = blockingStub.searchAll(Empty.getDefaultInstance()).getBookListList();
        tableView.setItems(FXCollections.observableList(books));
    }

    /**
     * Return list book as filter
     *
     * @param bookName
     * @param author
     * @return ObservableList<Book>
     */
    private ObservableList<Book> filterList(String bookName, String author) throws StatusRuntimeException {
        BookList filteredList = blockingStub.search(Search.newBuilder().setBookName(bookName).setAuthor(author).build());
        return FXCollections.observableList(filteredList.getBookListList());
    }

    /**
     * validate input
     *
     * @param bookName
     * @return boolean
     */
    private boolean validate(String bookName) {
        StringBuilder errors = new StringBuilder();
        if (bookName.trim().isEmpty()) {
            errors.append("- Please enter a book name.\n");
        }
        if (errors.length() > 0) {
            AlertUtil.showWarnAlert(errors.toString());
            return false;
        }
        return true;
    }

    /**
     * Clean all text field after add success
     */
    public void clean() {
        bookName.clear();
        author.clear();
    }
}
