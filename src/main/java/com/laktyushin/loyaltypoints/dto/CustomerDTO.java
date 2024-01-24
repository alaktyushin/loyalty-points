package com.laktyushin.loyaltypoints.dto;

import java.math.BigDecimal;

public class CustomerDTO {

	private Long id;

	private String firstName;

	private String lastName;
	private BigDecimal amount;

	public Long getId() {			// spring does use it
		return id;
	}

	public CustomerDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public CustomerDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public CustomerDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public CustomerDTO setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public String toString() {
		return "CustomerDTO{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", amount=" + amount +
				'}';
	}
}
