package com.laktyushin.loyaltypoints.controller;

import com.laktyushin.loyaltypoints.dto.CustomerDTO;
import com.laktyushin.loyaltypoints.enums.Roles;
import com.laktyushin.loyaltypoints.model.Customer;
import com.laktyushin.loyaltypoints.repository.CustomerRepository;
import com.laktyushin.loyaltypoints.service.CustomerMapper;
import com.laktyushin.loyaltypoints.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Tag(name = "Customer", description = "The Customer API. Contains all the operations that can be performed on a Customer.")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Operation(summary = "Get all Customers")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get All Customers"),
            @ApiResponse(responseCode = "400", description = "Bad Request / Unauthenticated")})
    @GetMapping("/customer")
    public Flux<CustomerDTO> getAllCustomers(@Parameter(hidden = true) RequestEntity<String> requestEntity) {
        Roles role = TokenUtils.getRoleFromRequest(requestEntity);
        if (role != Roles.ADMIN) {
            return Flux.error(new HttpMessageNotReadableException("Forbidden: ".concat(role.toString())));
        }
        Flux<Customer> customerFlux = customerRepository.findAll();
        return customerMapper.toDTO(customerFlux);
    }

    @Operation(summary = "Create Customer")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create Customer"),
            @ApiResponse(responseCode = "400", description = "Bad Request / Unauthenticated")})
    public Mono<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO,
                                            @Parameter(hidden = true) RequestEntity<String> requestEntity) {
        Roles role = TokenUtils.getRoleFromRequest(requestEntity);
        if (role != Roles.ADMIN) {
            return Mono.error(new HttpMessageNotReadableException("Forbidden: ".concat(role.toString())));
        }
        if (customerDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new HttpMessageNotReadableException("Problem with amount: ".concat(customerDTO.getAmount().toString())));
        }
        Customer customer = customerMapper.toEntity(customerDTO);
        Mono<Customer> savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Operation(summary = "Get Customer by ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get Customer By Id"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request / Unauthenticated")})
    @GetMapping("/customer/{id}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerById(@PathVariable(value = "id") Long customerId,
                                                             @Parameter(hidden = true) RequestEntity<String> requestEntity) {
        Roles role = TokenUtils.getRoleFromRequest(requestEntity);
        if (role != Roles.ADMIN) {
            return Mono.error(new HttpMessageNotReadableException("Forbidden: ".concat(role.toString())));
        }
        return customerRepository.findById(customerId).map(savedCustomer -> ResponseEntity.ok(customerMapper.toDTO(savedCustomer)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Customer")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update Customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request / Unauthenticated")})
    @PutMapping("/customer/{id}")
    public Mono<ResponseEntity<CustomerDTO>> updateCustomer(@PathVariable(value = "id") Long customerId,
                                                            @Valid @RequestBody CustomerDTO customerDTO,
                                                            @Parameter(hidden = true) RequestEntity<String> requestEntity) {
        Roles role = TokenUtils.getRoleFromRequest(requestEntity);
        if (role == Roles.UNREGISTERED) {
            return Mono.error(new HttpMessageNotReadableException("Forbidden: ".concat(role.toString())));
        }
        if (customerDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new HttpMessageNotReadableException("Problem with amount: ".concat(customerDTO.getAmount().toString())));
        }
        Mono<ResponseEntity<CustomerDTO>> responseEntityMono;
        responseEntityMono = customerRepository
                .findById(customerId)
                .flatMap(
                        existingCustomer -> {
                            BigDecimal oldAmount = existingCustomer.getAmount();
                            BigDecimal newAmount = customerMapper.toEntity(customerDTO).getAmount();
                            if ((newAmount.compareTo(oldAmount) > 0) && (role == Roles.COMMON_BUSINESS)) {
                                return Mono.error(new HttpMessageNotReadableException("Not allowed to award points: ".concat(customerDTO.getAmount().toString())));
                            }
                            existingCustomer
                                    .setFirstName(customerMapper.toEntity(customerDTO).getFirstName())
                                    .setLastName(customerMapper.toEntity(customerDTO).getLastName())
                                    .setAmount(newAmount);
                            return customerRepository.save(existingCustomer);
                        })
                .map(updateCustomer -> new ResponseEntity<>(customerMapper.toDTO(updateCustomer), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return responseEntityMono;
    }

    @Operation(summary = "Delete Customer")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request / Unauthenticated")})
    @DeleteMapping("/customer/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable(value = "id") Long customerId,
                                                     @Parameter(hidden = true) RequestEntity<String> requestEntity) {
        Roles role = TokenUtils.getRoleFromRequest(requestEntity);
        if (role != Roles.ADMIN) {
            return Mono.error(new HttpMessageNotReadableException("Forbidden: ".concat(role.toString())));
        }
        return customerRepository.findById(customerId)
                .flatMap(existingCustomer -> customerRepository.delete(existingCustomer)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
