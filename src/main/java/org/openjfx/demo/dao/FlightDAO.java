package org.openjfx.demo.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.openjfx.demo.database.Database;
import org.openjfx.demo.models.Flight;
import org.openjfx.demo.util.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightDAO {
    private static final String tableName = "flights";
    private static final ObservableList<Flight> flights = FXCollections.observableArrayList();

//    public static void updateFlightsFromDB() {
//        String query = "SELECT * FROM " + tableName;
//        try (Connection connection = Database.connect()) {
//            assert connection != null;
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet rs = statement.executeQuery();
//            flights.clear();
//            System.out.println("Before");
//            while (rs.next()) {
//                flights.add(mapResultSetToFlight(rs));
//                System.out.println("After");
//            }
//        } catch (SQLException e) {
//            Logger.getAnonymousLogger().log(
//                    Level.SEVERE,
//                    "Could not load Flights from the database");
//            flights.clear();
//        }
//    }

    public static void updateFlightsFromDB() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Flight> flights = session.createQuery("FROM Flight", Flight.class).list();
            FlightDAO.flights.clear();
            FlightDAO.flights.addAll(flights);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    "Could not load Flights from the database",
                    e
            );
            FlightDAO.flights.clear();
        }
    }
    public static ObservableList<Flight> getFlights() {
        return flights;
    }

    public static Optional<Flight> getFlight(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) return Optional.of(flight);
        }
        return Optional.empty();
    }
}
