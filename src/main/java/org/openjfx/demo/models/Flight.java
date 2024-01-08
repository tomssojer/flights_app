package org.openjfx.demo.models;

import java.time.LocalDate;
import java.util.List;

public class Flight {
    private int id;
    private String from;
    private String to;
    private LocalDate from_date;
    private LocalDate to_date;
    private List<Order> orders;

    public Flight(int id, String from, String to, LocalDate from_date, LocalDate to_date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
