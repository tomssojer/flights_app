package org.openjfx.flights;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Order;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("app.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}