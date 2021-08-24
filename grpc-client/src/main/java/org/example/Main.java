package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Book Form");
        primaryStage.setScene(new Scene(new CreateController()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
