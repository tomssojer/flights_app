package org.openjfx.flights.models;

import java.util.List;

public class Order {
    private int id;
    private String card;
    private int flight_id;
    private String first_name;
    private String last_name;
    private String passport;
    private String address;
    private int suitcases;
    private String razred;
    private String food;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", card='" + card + '\'' +
                ", flight_id=" + flight_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", suitcases=" + suitcases +
                ", razred='" + razred + '\'' +
                ", food='" + food + '\'' +
                '}';
    }

    public Order(int id, String card, int flight_id, String first_name, String last_name, String passport, String address, int suitcases, String razred, String food) {
        this.id = id;
        this.card = card;
        this.flight_id = flight_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.passport = passport;
        this.address = address;
        this.suitcases = suitcases;
        this.razred = razred;
        this.food = food;
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

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSuitcases() {
        return suitcases;
    }

    public void setSuitcases(int suitcases) {
        this.suitcases = suitcases;
    }

    public String getRazred() {
        return razred;
    }

    public void setRazred(String razred) {
        this.razred = razred;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
