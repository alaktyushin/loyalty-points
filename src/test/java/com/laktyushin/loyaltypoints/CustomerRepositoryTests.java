package com.laktyushin.loyaltypoints;

import com.laktyushin.loyaltypoints.model.Customer;
import com.laktyushin.loyaltypoints.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
public class CustomerRepositoryTests {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private CustomerRepository customers;

    @Test
    public void testFindByLastName() {
        Customer customer = new Customer("AIvan", "Ivanov");
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(databaseClient, H2Dialect.INSTANCE);
        template.insert(Customer.class).using(customer).then().as(StepVerifier::create).verifyComplete();
        Flux<Customer> findByLastName = customers.findByLastName(customer.getLastName());

        findByLastName.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getFirstName()).isEqualTo("AIvan");
                assertThat(actual.getLastName()).isEqualTo("Ivanov");
            })
            .verifyComplete();
    }
}
