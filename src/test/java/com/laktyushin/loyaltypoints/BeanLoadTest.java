package com.laktyushin.loyaltypoints;

import com.laktyushin.loyaltypoints.controller.CustomerController;
import com.laktyushin.loyaltypoints.controller.CustomerMapper;
import com.laktyushin.loyaltypoints.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeanLoadTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    void testContextLoads() {
    }

    @Test
    void testCustomerControllerLoads() throws Exception {
        assertThat(customerController).isNotNull();
    }

    @Test
    void testCustomerMapperLoads() throws Exception {
        assertThat(customerMapper).isNotNull();
    }

    @Test
    void testCustomerRepositoryLoads() throws Exception {
        assertThat(customerRepository).isNotNull();
    }
}
