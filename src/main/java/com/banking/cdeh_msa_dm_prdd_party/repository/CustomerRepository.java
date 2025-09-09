package com.banking.cdeh_msa_dm_prdd_party.repository;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, UUID> {
    @Query("UPDATE customer SET status = false WHERE customer_id = :customerId")
    Mono<Integer> deactivateCustomerById(@Param("customerId") UUID customerId);

    @Query("SELECT * FROM customer WHERE status = true")
    Flux<Customer> findAllActiveCustomers();

    @Query("SELECT * FROM customer WHERE customer_id = :customerId AND status = true")
    Mono<Customer> findActiveCustomerById(@Param("customerId") UUID customerId);
}
