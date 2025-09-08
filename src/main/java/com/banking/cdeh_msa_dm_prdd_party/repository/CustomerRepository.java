package com.banking.cdeh_msa_dm_prdd_party.repository;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, UUID> {
}

