package com.laktyushin.loyaltypoints.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Customer {

    @Id
    private Long id;            // UUID should be better, but for demo purposes Long is OK, especially for in-memory H2
    private String firstName;
    private String lastName;
    private BigDecimal amount;
    public Customer() {
        this.amount = BigDecimal.ZERO;
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Customer setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
