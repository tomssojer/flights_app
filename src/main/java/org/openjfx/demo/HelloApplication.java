package org.openjfx.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.openjfx.demo.dao.FlightDAO;
import org.openjfx.demo.models.Flight;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {

    private Parent root;
    private void displayFlights() {
        ObservableList<Flight> flights = FlightDAO.getFlights();

        System.out.println("List of Flights:");
        int count = 0;
        for (Flight flight : flights) {
            System.out.println(flight.toString());
            count++;
            if (count == 10) {
                return;
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello.fxml"));
        root = fxmlLoader.load();
        HelloController controller = fxmlLoader.getController();


        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}