package org.example;

import javafx.scene.layout.HBox;
import org.example.util.FXMLUtil;

public class TopMenuController extends HBox {

    public TopMenuController() {
        FXMLUtil.loadFXML("top", this);
    }
}
