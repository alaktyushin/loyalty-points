package com.laktyushin.loyaltypoints.repository;

import com.laktyushin.loyaltypoints.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    //@Query("SELECT * FROM customer WHERE last_name = :lastname")
    Flux<Customer> findByLastName(String lastName);

}
