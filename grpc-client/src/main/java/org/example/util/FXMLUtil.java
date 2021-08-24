package org.example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FXMLUtil {
    private static final String FXML_SUFFIX = ".fxml";

    public static void loadFXML(String fileName, Node node) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(node.getClass().getResource( fileName + FXML_SUFFIX));
        loader.setRoot(node);
        loader.setController(node);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
