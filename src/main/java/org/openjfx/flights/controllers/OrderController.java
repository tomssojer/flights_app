package org.openjfx.flights.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.openjfx.flights.dao.FlightDAO;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Flight;
import org.openjfx.flights.models.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class OrderController {
    @FXML
    private Label imeLabel;
    @FXML
    private Label destinacijaLabel;
    @FXML
    private Label odhodLabel;
    @FXML
    private Label povratekLabel;
    @FXML
    private Label razredLabel;
    @FXML
    private Label meniLabel;
    @FXML
    private Label prtljagaLabel;
    @FXML
    private Label karticaLabel;

    @FXML
    private Button odstraniButton;


    public void initialize(Order order) {
        imeLabel.setText(order.getFirst_name() + " " + order.getLast_name());

        Optional<Flight> flight = FlightDAO.getFlight(order.getFlight_id());
        flight.ifPresent(let -> {
            String from_loc = let.getFrom_loc();
            String to_loc = let.getTo_loc();
            destinacijaLabel.setText(from_loc + " -> " + to_loc);

            String from_date = String.valueOf(let.getFrom_date());
            odhodLabel.setText(odhodLabel.getText() + from_date);
            String to_date = String.valueOf(let.getTo_date());
            povratekLabel.setText(povratekLabel.getText() + to_date);
        });

        razredLabel.setText(razredLabel.getText() + order.getRazred());
        meniLabel.setText(meniLabel.getText() + order.getFood());
        prtljagaLabel.setText(prtljagaLabel.getText() + order.getSuitcases());

        String kartica = order.getCard();
        if (kartica.length() > 4) {
            String maskedPart = "xxxx-xxxx-xxxx-";
            int lastFourDigitsIndex = kartica.length() - 4;
            String lastFourDigits = kartica.substring(lastFourDigitsIndex);
            karticaLabel.setText(karticaLabel.getText() + maskedPart + lastFourDigits);

        } else karticaLabel.setText(karticaLabel.getText() + kartica);
    }
    public void addOrderContainer(VBox parent, Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/flights/order.fxml"));
            Node orderContainer = loader.load();
            OrderController orderController = loader.getController();
            orderController.initialize(order);

            parent.getChildren().add(orderContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void deleteOrder() {
//        OrderDAO.delete();
//    }
}
