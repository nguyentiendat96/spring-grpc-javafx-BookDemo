package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.util.FXMLUtil;

public class LeftMenuController extends VBox {

    @FXML
    private Button create;

    @FXML
    private Button search;

    public LeftMenuController() {
        FXMLUtil.loadFXML("left", this);
        create.setOnAction(event -> showCreateForm());
        search.setOnAction(event -> showSearchForm());
    }

    public void showCreateForm() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/create.fxml"));
        loader.setRoot(new CreateController());
        loader.setController(new CreateController());
        Stage window = (Stage) create.getScene().getWindow();
        window.setScene(new Scene(new CreateController()));
    }

    public void showSearchForm() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/search.fxml"));
        loader.setRoot(new SearchController());
        loader.setController(new SearchController());
        Stage window = (Stage) search.getScene().getWindow();
        window.setScene(new Scene(new SearchController()));
    }
}
