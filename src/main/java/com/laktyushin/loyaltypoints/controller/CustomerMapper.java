package com.laktyushin.loyaltypoints.controller;


import com.laktyushin.loyaltypoints.dto.CustomerDTO;
import com.laktyushin.loyaltypoints.model.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerMapper {

    Flux<CustomerDTO> toDTO(Flux<Customer> customerFlux) {
        return customerFlux.map(this::toDTO);
    }

    Mono<CustomerDTO> toDTO(Mono<Customer> customerMono) {
        return customerMono.map(this::toDTO);
    }

    CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO()
                .setId(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setAmount(customer.getAmount());
    }

    Customer toEntity(CustomerDTO customerDTO) {
        return new Customer()
                .setFirstName(customerDTO.getFirstName())
                .setLastName(customerDTO.getLastName())
                .setAmount(customerDTO.getAmount());
    }

}
