package org.openjfx.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.openjfx.demo.dao.FlightDAO;
import org.openjfx.demo.models.Flight;

import java.net.URL;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
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
    private ListView<Flight> flightsListView = new ListView<>();
    private final String[] from_locations = {"Ljubljana", "Trst", "Benetke", "Zagreb", "Dunaj", "Munich"};
    private final String[] to_locations = {"Berlin", "Madrid", "Paris", "London", "Praga", "Rim", "Bern", "Varšava",
            "Amsterdam", "Frankfurt", "Bruselj", "New York", "Barcelona"};

    public void searchFlights() {
        String odkod = odkodCombobox.getValue();
        String kam = kamCombobox.getValue();
        LocalDate odhod = odhodPicker.getValue();
        LocalDate prihod = prihodPicker.getValue();
        int seats = seatsSpinner.getValue();

        ObservableList<Flight> searchResults = FlightDAO.filterFlights(odkod, kam, odhod, prihod, seats);
        System.out.println(searchResults);

        flightsListView.setPrefHeight(100);
        flightsListView.setPrefWidth(300);
        letiContainer.getChildren().add(flightsListView);
        flightsListView.setItems(searchResults);
    }

    private void formatDate(DatePicker picker) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        odkodCombobox.getItems().addAll(from_locations);
        kamCombobox.getItems().addAll(to_locations);
        formatDate(odhodPicker);
        formatDate(prihodPicker);

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