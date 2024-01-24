package com.laktyushin.loyaltypoints;

import com.laktyushin.loyaltypoints.controller.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HttpServerTest {

    private final URI CUSTOMER_URI = new URI("http://localhost:8088/customer");

    @Autowired
    private CustomerController customerController;

    public HttpServerTest() throws URISyntaxException {
    }

    @Test
    void endpointIsAlive() {
        assertThat(this.customerController.getAllCustomers(new RequestEntity<>(HttpMethod.GET, CUSTOMER_URI))).isNotNull();
    }
}
