package org.openjfx.flights.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.openjfx.flights.dao.FlightDAO;
import org.openjfx.flights.models.Flight;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> odkodCombobox;
    @FXML
    private ComboBox<String> kamCombobox;
    @FXML
    private DatePicker odhodPicker;
    @FXML
    private DatePicker prihodPicker;
    @FXML
    private Spinner<Integer> seatsSpinner;
    @FXML
    private Button isciButton;
    @FXML
    private VBox letiContainer;
    private static final ListView<Flight> flightsListView = new ListView<>();
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
            noFlightsLabel.setText("Ni letov za izbrani filter");
            letiContainer.getChildren().add(noFlightsLabel);
        } else {
            System.out.println(searchResults.size());
            int count = 0;
            int limit = 20;
            for (Flight flight : searchResults) {
                FlightContainerController flightContainerController = new FlightContainerController();
                flightContainerController.addFlightContainer(
                        letiContainer,
                        flight.getId(),
                        flight.getFrom_loc(),
                        flight.getTo_loc(),
                        flight.getFrom_date(),
                        flight.getTo_date(),
                        flight.getPrice(),
                        flight.getMax_seats(),
                        flight.getOrders()
                );
                count++;
                if (count >= limit) break;
            }
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

            // Access the controller and set data from the selected flight
            ModalController modalController = loader.getController();
            modalController.initialize(selectedFlight);

            // Create a new dialog or scene to display the modal
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Flight Reservation");
            modalStage.setScene(new Scene(root));

            modalStage.showAndWait(); // This will wait for the modal to be closed
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
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