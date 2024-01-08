package org.openjfx.demo.models;

import java.util.List;

public class Order {
    private int id;
    private String card;
    private Flight flight;
    private List<Seat> seats;

    public Order(int id, String card, Flight flight, List<Seat> seats) {
        this.id = id;
        this.card = card;
        this.flight = flight;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
