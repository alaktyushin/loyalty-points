package com.laktyushin.loyaltypoints.service;


import com.laktyushin.loyaltypoints.dto.CustomerDTO;
import com.laktyushin.loyaltypoints.model.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerMapper {

    public Flux<CustomerDTO> toDTO(Flux<Customer> customerFlux) {
        return customerFlux.map(this::toDTO);
    }

    public Mono<CustomerDTO> toDTO(Mono<Customer> customerMono) {
        return customerMono.map(this::toDTO);
    }

    public CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO()
                .setId(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setAmount(customer.getAmount());
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        return new Customer()
                .setFirstName(customerDTO.getFirstName())
                .setLastName(customerDTO.getLastName())
                .setAmount(customerDTO.getAmount());
    }

}
