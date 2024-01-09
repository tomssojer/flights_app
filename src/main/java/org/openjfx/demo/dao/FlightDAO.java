package org.openjfx.demo.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.demo.database.Database;
import org.openjfx.demo.models.Flight;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightDAO {
    private static final String tableName = "flights";
    private static final ObservableList<Flight> flights = FXCollections.observableArrayList();

    static {
        updateFlightsFromDB();
    }

    public static void updateFlightsFromDB() {
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            flights.clear();
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getInt("id"),
                    rs.getString("from_loc"),
                    rs.getString("to_loc"),
                    LocalDate.parse(rs.getString("from_date").trim()),
                    LocalDate.parse(rs.getString("to_date").trim()),
                    rs.getInt("max_seats"),
                    rs.getInt("orders")));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    "Could not load Flights from the database");
            flights.clear();
        }
    }

    public static ObservableList<Flight> getFlights() {
        updateFlightsFromDB();
        return FXCollections.unmodifiableObservableList(flights);
    }

    public static Optional<Flight> getFlight(int id) {
        updateFlightsFromDB();
        for (Flight flight : flights) {
            if (flight.getId() == id) return Optional.of(flight);
        }
        return Optional.empty();
    }

    public static void updateFlight(Flight flight) {
        int rows = Helper.update(
                tableName,
                new String[]{"from_loc", "to_loc", "from_date", "to_date", "max_seats", "orders"},
                new Object[]{flight.getFrom_loc(), flight.getTo_loc(), flight.getFrom_date(), flight.getTo_date(), flight.getMax_seats(), flight.getOrders()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER},
                "id",
                Types.INTEGER,
                flight.getId()
        );

        System.out.println(rows);
        if (rows == 0)
            throw new IllegalStateException("Flight to be updated with id " + flight.getId() + " didn't exist in database");

        //update cache
        Optional<Flight> optionalFlight = getFlight(flight.getId());
        optionalFlight.ifPresentOrElse((oldFlight) -> {
            flights.remove(oldFlight);
            flights.add(flight);
        }, () -> {
            throw new IllegalStateException("FLight to be updated with id " + flight.getId() + " didn't exist in database");
        });
    }
}
