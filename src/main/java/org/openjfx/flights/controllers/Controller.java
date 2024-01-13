package org.openjfx.flights.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.hibernate.mapping.ValueVisitor;
import org.openjfx.flights.dao.FlightDAO;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Flight;
import org.openjfx.flights.models.Order;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private MenuItem closeMenuitem;
    @FXML
    private ComboBox<String> odkodCombobox;
    @FXML
    private ComboBox<String> kamCombobox;
    @FXML
    private DatePicker odhodPicker;
    @FXML
    private DatePicker prihodPicker;
    @FXML
    private VBox letiContainer;
    @FXML
    private VBox orderContainer;
    @FXML
    private ScrollPane scrollpaneParent;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label rezultatiLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private HBox statusBox;

    private final String[] from_locations = {"Ljubljana", "Trst", "Benetke", "Zagreb", "Dunaj", "Munich"};
    private final String[] to_locations = {"Berlin", "Madrid", "Paris", "London", "Praga", "Rim", "Bern", "Varšava",
            "Amsterdam", "Frankfurt", "Bruselj", "New York", "Barcelona"};

    public void searchFlights() {
        letiContainer.getChildren().clear();

        String odkod = odkodCombobox.getValue();
        String kam = kamCombobox.getValue();
        LocalDate odhod = odhodPicker.getValue();
        LocalDate prihod = prihodPicker.getValue();

        ObservableList<Flight> searchResults = FlightDAO.filterFlights(odkod, kam, odhod, prihod);

        if (searchResults.isEmpty()) {
            Label noFlightsLabel = new Label();
            noFlightsLabel.setText("Ni letov za izbrani filter.");
            letiContainer.getChildren().add(noFlightsLabel);
        } else {
            int count = 0;
            int limit = 20;
            for (Flight flight : searchResults) {
                FlightContainerController flightContainerController = new FlightContainerController();
                flightContainerController.addFlightContainer(letiContainer, flight);
                count++;
                if (count >= limit) break;
            }

            showStatusBox(statusBox, statusLabel, "available flights loaded");
        }
    }

    private void formatDate(DatePicker picker) {
        picker.setDayCellFactory(newPicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.isBefore(today));
            }
        });

        picker.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateFormatter.format(object);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public static void flightClicked(Flight selectedFlight) {
        try {
            FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/org/openjfx/flights/modal.fxml"));
            Parent root = loader.load();

            ModalController modalController = loader.getController();
            modalController.initialize(selectedFlight);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Rezerviraj let");
            modalStage.setScene(new Scene(root));

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadOrders() throws IOException {

        if (orderContainer != null) orderContainer.getChildren().clear();
        assert orderContainer != null;
        ObservableList<Order> searchResults = OrderDAO.getOrders();

        if (searchResults.isEmpty()) {
            Label noOrdersLabel = new Label();
            noOrdersLabel.setText("Ni še nobenih naročil.");
            orderContainer.getChildren().add(noOrdersLabel);
        } else {
            for (Order order : searchResults) {
                OrderController orderController = new OrderController();
                orderController.addOrderContainer(orderContainer, order);
            }
        }
    }

    private void showStatusBox(HBox box, Label label, String output) {
        label.setText("Status: " + output);
        box.setOpacity(1);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), box);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();
                })
        );
        fadeTransition.play();
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (odkodCombobox != null) odkodCombobox.getItems().addAll(from_locations);
        if (kamCombobox != null) kamCombobox.getItems().addAll(to_locations);
        if (prihodPicker != null) formatDate(prihodPicker);

        if (odhodPicker != null) {
            formatDate(odhodPicker);
            odhodPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    prihodPicker.setDayCellFactory(picker -> new DateCell() {
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);
                            setDisable(empty || date.isBefore(newValue) || date.isEqual(newValue));
                        }
                    });
                }
            });
        }
    }
}