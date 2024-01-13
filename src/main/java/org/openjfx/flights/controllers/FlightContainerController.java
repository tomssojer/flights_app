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

    public void initialize(Flight flight) {
        flightLabel.setText(flight.getFrom_loc() + " -> " + flight.getTo_loc());
        dateLabel.setText("Datum " + flight.getFrom_date());
        seatsLabel.setText("Prosta mesta " + (flight.getMax_seats()-flight.getOrders()));
        priceLabel.setText("Cena " + flight.getPrice() + " €");

        flightAnchor.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Flight selectedFlight = new Flight(
                        flight.getId(),
                        flight.getFrom_loc(),
                        flight.getTo_loc(),
                        flight.getFrom_date(),
                        flight.getTo_date(),
                        flight.getPrice(),
                        flight.getMax_seats(),
                        flight.getOrders());
                Controller.flightClicked(selectedFlight);
            }
        });
    }

    public void addFlightContainer(VBox parent, Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/flights/flight.fxml"));
            Node flightContainer = loader.load();
            flightContainer.setCursor(Cursor.HAND);
            FlightContainerController flightController = loader.getController();
            flightController.initialize(flight);

            parent.getChildren().add(flightContainer);
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
    }
}
