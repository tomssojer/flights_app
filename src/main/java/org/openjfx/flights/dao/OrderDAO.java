package org.openjfx.flights.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.flights.database.Database;
import org.openjfx.flights.models.Order;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    private static final String tableName = "orders";
    private static final ObservableList<Order> orders = FXCollections.observableArrayList();


    public static void updateOrdersFromDB() {
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            orders.clear();
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("card"),
                        rs.getInt("flight_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("passport"),
                        rs.getString("address"),
                        rs.getInt("suitcases"),
                        rs.getString("razred"),
                        rs.getString("food")));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    "Could not load Flights from the database");
            orders.clear();
        }
    }

    public static ObservableList<Order> getOrders() {
        updateOrdersFromDB();
        return FXCollections.unmodifiableObservableList(orders);
    }

    public static void createOrder(String card, int flight_id, String first_name, String last_name, String passport, String address, int suitcases, String razred, String food) {
        Helper.create(
            tableName,
            new String[]{"card", "flight_id", "first_name", "last_name", "passport", "address", "suitcases", "razred", "food"},
            new Object[]{card, flight_id, first_name, last_name, passport, address, suitcases, razred, food},
            new int[]{Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR}
        );
    }

    public static void delete(int id) {
        //update database
        Helper.delete(tableName, id);
    }

    public static Optional<Order> getOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) return Optional.of(order);
        }
        return Optional.empty();
    }
}
