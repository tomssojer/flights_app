package org.openjfx.flights.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Flight;

public class ModalController {
    @FXML
    private TextField imeField;
    @FXML
    private TextField priimekField;
    @FXML
    private TextField naslovField;
    @FXML
    private TextField mestoField;
    @FXML
    private TextField postnaField;
    @FXML
    private TextField potniField;
    @FXML
    private TextField karticaField;
    @FXML
    private ComboBox<String> karticaCombobox;
    @FXML
    private RadioButton ekonomskiRadio;
    @FXML
    private RadioButton prviRadio;
    @FXML
    private RadioButton mesniRadio;
    @FXML
    private RadioButton vegiRadio;
    @FXML
    private RadioButton osnovnaRadio;
    @FXML
    private RadioButton vecjaRadio;

    @FXML
    private Button prekliciButton;
    @FXML
    private Button rezervirajButton;

    @FXML
    private Label podatkiLabel;
    @FXML
    private Label letLabel;
    @FXML
    private Label datumLabel;

    private int flight_id;
    private final String[] kartice = {"Visa", "Mastercard", "American Express"};

    public void initialize(Flight selectedFlight) {
        flight_id = selectedFlight.getId();
        if (karticaCombobox != null) karticaCombobox.getItems().addAll(kartice);

        podatkiLabel.setText(podatkiLabel.getText() + " " + selectedFlight.getId());
        letLabel.setText(selectedFlight.getFrom_loc() + " -> " + selectedFlight.getTo_loc());
        datumLabel.setText(selectedFlight.getFrom_date().toString());
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) prekliciButton.getScene().getWindow();
        stage.close();
    }

    public void reserveOnAction(ActionEvent actionEvent) {

        String card = karticaField.getText();
        String first_name = imeField.getText();
        String last_name = priimekField.getText();
        String address = naslovField.getText() + ", " + postnaField.getText() + ", " + mestoField.getText();
        String passport = potniField.getText();
        int suitcases;
        if (osnovnaRadio.isSelected()) suitcases = 20;
        else suitcases = 30;
        String razred;
        if (ekonomskiRadio.isSelected()) razred = ekonomskiRadio.getText();
        else razred = prviRadio.getText();
        String food;
        if (mesniRadio.isSelected()) food = mesniRadio.getText();
        else food = vegiRadio.getText();

        OrderDAO.createOrder(
                card,
                flight_id,
                first_name,
                last_name,
                passport,
                address,
                suitcases,
                razred,
                food
        );
    }
}
