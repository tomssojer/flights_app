package org.openjfx.flights.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.flights.dao.OrderDAO;
import org.openjfx.flights.models.Order;

import java.io.IOException;

public class DeletePromptController {
    private final OrderController orderController;
    @FXML
    private Label nameLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button prekliciButton;
    @FXML
    private Button izbrisiButton;

    private Order order;
    Stage thisStage = new Stage();

    public DeletePromptController(OrderController orderController) {
        this.orderController = orderController;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/flights/deletePrompt.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Izbris rezervacije");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        this.order = orderController.getOrder();

        nameLabel.setText(order.getFirst_name() + " " + order.getLast_name());
        locationLabel.setText(orderController.getFlight().getFrom_loc() + " -> " + orderController.getFlight().getTo_loc());

        prekliciButton.setOnAction(event -> closePrompt());
        izbrisiButton.setOnAction(event -> {
            try {
                deleteOrder(order);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void closePrompt() {
        Stage stage = (Stage) prekliciButton.getScene().getWindow();
        stage.close();
    }


    public void deleteOrder(Order order) throws IOException {

        OrderDAO.delete(order.getId());

        Controller controller = new Controller();
        controller.loadOrders();

        Stage stage = (Stage) izbrisiButton.getScene().getWindow();
        stage.close();
    }
}
