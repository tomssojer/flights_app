package org.openjfx.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.demo.dao.FlightDAO;
import org.openjfx.demo.models.Flight;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    private void displayFlights() {
        ObservableList<Flight> flights = FlightDAO.getFlights();

        System.out.println("List of Flights:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.show();

        // Load flights from the database
        FlightDAO.updateFlightsFromDB();

        // Display flights in the terminal
        displayFlights();

        Optional<Flight> flight = FlightDAO.getFlight(1);
        System.out.println(flight);
    }

    public static void main(String[] args) {
        launch(args);
    }
}