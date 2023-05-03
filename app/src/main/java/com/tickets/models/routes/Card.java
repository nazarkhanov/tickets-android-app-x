package com.tickets.models.routes;

public class Card {
    private long price;
    private String currency;

    public Card() {
    }

    public Card(long price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
