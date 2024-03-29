package com.openlol.api.model;

public class Transaction {
    private String userId;
    private String type;
    private Double amount;
    //some other transaction properties
    //...
    private Object creditCard;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Object getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Object creditCard) {
        this.creditCard = creditCard;
    }
}