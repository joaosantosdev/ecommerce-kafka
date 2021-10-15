package br.com.ourmind.fraudDetector.models;

import java.math.BigDecimal;

public class Order {
    public final String userId, id;
    public final BigDecimal amount;

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Order(String userId, String id, BigDecimal amount) {
        this.userId = userId;
        this.id = id;
        this.amount = amount;
    }

}
