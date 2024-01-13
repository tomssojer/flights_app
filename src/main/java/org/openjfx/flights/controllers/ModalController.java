package org.openjfx.flights.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.boot.archive.scan.internal.StandardScanner;
import org.hibernate.boot.model.source.internal.OverriddenMappingDefaults;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Flight;

import java.util.Objects;

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
    private TextField stevilkaKarticeField;
    @FXML
    private TextField zascitnaKodaField;
    @FXML
    private TextField mailField;
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
    private DatePicker datumPicker;

    @FXML
    private Button prekliciButton;
    @FXML
    private Button ponastaviButton;
    @FXML
    private Button rezervirajButton;

    @FXML
    private Label podatkiLabel;
    @FXML
    private Label letLabel;
    @FXML
    private Label datumOdhodaLabel;
    @FXML
    private Label datumPovratkaLabel;
    @FXML
    private Label emptyLabel;

    private int flight_id;
    private final String[] kartice = {"Visa", "Mastercard", "American Express"};

    public void initialize(Flight selectedFlight) {
        flight_id = selectedFlight.getId();
        if (karticaCombobox != null) karticaCombobox.getItems().addAll(kartice);

        letLabel.setText(letLabel.getText() + selectedFlight.getFrom_loc() + " -> " + selectedFlight.getTo_loc());
        datumOdhodaLabel.setText(datumOdhodaLabel.getText() + selectedFlight.getFrom_date().toString());
        datumPovratkaLabel.setText(datumPovratkaLabel.getText() + selectedFlight.getTo_date().toString());

        // Radio buttons
        osnovnaRadio.setOnAction(event -> vecjaRadio.setSelected(false));
        vecjaRadio.setOnAction(event -> osnovnaRadio.setSelected(false));
        ekonomskiRadio.setOnAction(event -> prviRadio.setSelected(false));
        prviRadio.setOnAction(event -> ekonomskiRadio.setSelected(false));
        mesniRadio.setOnAction(event -> vegiRadio.setSelected(false));
        vegiRadio.setOnAction(event -> mesniRadio.setSelected(false));

        addListeners(imeField);
        addListeners(priimekField);
        addListeners(naslovField);
        addListeners(mestoField);
        addListeners(postnaField);
        addListeners(potniField);
        addListeners(karticaField);
        addListeners(stevilkaKarticeField);
        addListeners(zascitnaKodaField);
        addListeners(mailField);
        addListeners(ekonomskiRadio, prviRadio);
        addListeners(prviRadio, ekonomskiRadio);
        addListeners(mesniRadio, vegiRadio);
        addListeners(vegiRadio, mesniRadio);
        addListeners(osnovnaRadio, vecjaRadio);
        addListeners(vecjaRadio, osnovnaRadio);

        setupCreditCardFormatter();
    }

    private void setupCreditCardFormatter() {
        stevilkaKarticeField.textProperty().addListener((observable, oldValue, newValue) -> {
            int valLength = newValue.length();
            if (valLength >= 20) stevilkaKarticeField.setText(oldValue);

            if (valLength > 0 && valLength % 5 == 0 && valLength != 20) {
                newValue = newValue.substring(0, valLength - 1) + "-";
                stevilkaKarticeField.setText(newValue);
                stevilkaKarticeField.positionCaret(valLength);
            }
        });
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) prekliciButton.getScene().getWindow();
        stage.close();
    }

    public void resetOrder() {
        resetTextField(imeField);
        resetTextField(priimekField);
        resetTextField(naslovField);
        resetTextField(mestoField);
        resetTextField(postnaField);
        resetTextField(potniField);
        resetTextField(karticaField);
        resetTextField(stevilkaKarticeField);
        resetTextField(zascitnaKodaField);
        resetTextField(mailField);
        resetRadioButton(ekonomskiRadio);
        resetRadioButton(prviRadio);
        resetRadioButton(mesniRadio);
        resetRadioButton(vegiRadio);
        resetRadioButton(osnovnaRadio);
        resetRadioButton(vecjaRadio);
        datumPicker.setValue(null);
        datumPicker.setStyle("-fx-border-color: transparent;");
        karticaCombobox.setStyle("-fx-border-color: transparent;");
        karticaCombobox.setValue(karticaCombobox.getPromptText());
    }

    public void reserveOnAction(ActionEvent actionEvent) {

        boolean isOkay = true;

        // Handle textfields
        String card = karticaField.getText();
        if (card.isEmpty()) isOkay = markEmptyField(karticaField);
        String first_name = imeField.getText();
        if (first_name.isEmpty()) isOkay = markEmptyField(imeField);
        String last_name = priimekField.getText();
        if (last_name.isEmpty()) isOkay = markEmptyField(priimekField);
        String address = naslovField.getText() + ", " + postnaField.getText() + ", " + mestoField.getText();
        if (naslovField.getText().isEmpty()) isOkay = markEmptyField(naslovField);
        if (postnaField.getText().isEmpty()) isOkay = markEmptyField(postnaField);
        if (mestoField.getText().isEmpty()) isOkay = markEmptyField(mestoField);
        String passport = potniField.getText();
        if (passport.isEmpty()) isOkay = markEmptyField(potniField);
        if (mailField.getText().isEmpty()) isOkay = markEmptyField(mailField);
        if (stevilkaKarticeField.getText().isEmpty()) isOkay = markEmptyField(stevilkaKarticeField);
        if (zascitnaKodaField.getText().isEmpty()) isOkay = markEmptyField(zascitnaKodaField);
        if (datumPicker.getValue() == null) datumPicker.setStyle("-fx-border-color: red;");
        if (Objects.equals(karticaCombobox.getValue(), karticaCombobox.getPromptText())) karticaCombobox.setStyle("-fx-border-color: red;");

        // Handle buttons
        int suitcases = 0;
        if (osnovnaRadio.isSelected()) suitcases = 20;
        else if (vecjaRadio.isSelected()) suitcases = 30;
        else isOkay = markEmptyButton(osnovnaRadio, vecjaRadio);
        String razred = null;
        if (ekonomskiRadio.isSelected()) razred = ekonomskiRadio.getText();
        else if (prviRadio.isSelected()) razred = prviRadio.getText();
        else isOkay = markEmptyButton(ekonomskiRadio, prviRadio);
        String food = null;
        if (mesniRadio.isSelected()) food = mesniRadio.getText();
        else if (vegiRadio.isSelected()) food = vegiRadio.getText();
        else isOkay = markEmptyButton(mesniRadio, vegiRadio);

        if (isOkay) {
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
            Stage stage = (Stage) rezervirajButton.getScene().getWindow();
            stage.close();
        } else {
            emptyLabel.setText("Dopolni vsa polja");
            emptyLabel.setStyle("-fx-text-fill: red");

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), emptyLabel);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);

            TranslateTransition pauseTransition = new TranslateTransition(Duration.seconds(10));
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.1), emptyLabel);
            fadeOutTransition.setFromValue(1);
            fadeOutTransition.setToValue(0);

            SequentialTransition sequentialTransition = new SequentialTransition(
                    fadeTransition,
                    pauseTransition,
                    fadeOutTransition
            );
            sequentialTransition.play();
        }
    }

    private boolean markEmptyField(TextField field) {
        field.setStyle("-fx-border-color: red;");
        return false;
    }

    private boolean markEmptyButton(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) radioButton.setStyle("-fx-border-color: red;");
        return false;
    }

    private void resetTextField(TextField textField) {
        textField.setText("");
        textField.setStyle("-fx-border-color: transparent;");
    }

    private void resetRadioButton(RadioButton radioButton) {
        radioButton.setSelected(false);
        radioButton.setStyle("-fx-border-color: transparent;");
    }

    private void addListeners(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: transparent;");
        });
    }

    private void addListeners(RadioButton radioButton, RadioButton secondButton) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            radioButton.setStyle("-fx-border-color: transparent;");
            secondButton.setStyle("-fx-border-color: transparent");
        });
    }

}
