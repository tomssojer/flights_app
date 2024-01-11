package org.openjfx.flights.models;

import java.time.LocalDate;

public class Flight {
    private int id;
    private String from_loc;
    private String to_loc;
    private LocalDate from_date;
    private LocalDate to_date;
    private int price;
    private int max_seats;
    private int orders;

    public Flight(int id, String from_loc, String to_loc, LocalDate from_date, LocalDate to_date, int price, int max_seats, int orders) {
        this.id = id;
        this.from_loc = from_loc;
        this.to_loc = to_loc;
        this.from_date = from_date;
        this.to_date = to_date;
        this.price = price;
        this.max_seats = max_seats;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
    }

    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
    }

    public LocalDate getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from_loc='" + from_loc + '\'' +
                ", to_loc='" + to_loc + '\'' +
                ", from_date=" + from_date +
                ", to_date=" + to_date +
                ", max_seats=" + max_seats +
                ", orders=" + orders +
                '}';
    }
}
