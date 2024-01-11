package org.openjfx.flights.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.openjfx.flights.models.Flight;

import java.io.IOException;
import java.time.LocalDate;

public class FlightContainerController {
    @FXML
    private Label flightLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label seatsLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private AnchorPane flightAnchor;

    public void initialize(int id, String from_loc, String to_loc, LocalDate from_date, LocalDate to_date, int price, int seats, int orders) {
        flightLabel.setText("Let " + id);
        dateLabel.setText("Datum " + from_date);
        seatsLabel.setText("Prosta mesta " + (seats-orders));
        priceLabel.setText("Cena " + price + " €");

        flightAnchor.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                System.out.println("Flight clicked: " + id);
                Flight selectedFlight = new Flight(id, from_loc, to_loc, from_date, to_date, price, seats, orders);
                Controller.flightClicked(selectedFlight);
            }
        });
    }

    public void addFlightContainer(VBox parent, int id, String from_loc, String to_loc, LocalDate from_date, LocalDate to_date, int price, int seats, int orders) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/flights/flight.fxml"));
            Node flightContainer = loader.load();
            flightContainer.setCursor(Cursor.HAND);
            FlightContainerController flightController = loader.getController();
            flightController.initialize(id, from_loc, to_loc, from_date, to_date, price, seats, orders);

            parent.getChildren().add(flightContainer);
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
    }
}
