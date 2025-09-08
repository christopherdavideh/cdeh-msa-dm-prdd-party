package com.banking.cdeh_msa_dm_prdd_party.service;

import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CustomerService {
    Mono<CustomerResponseDto> createCustomerWithParty(CustomerRequestDto input);
    Flux<CustomerResponseDto> getAllCustomerDTOs();
    Mono<CustomerResponseDto> getCustomerDTOById(UUID customerId);
    Mono<CustomerResponseDto> updateCustomerWithParty(UUID customerId, CustomerRequestDto input);
    Mono<Void> deleteCustomer(UUID customerId);
}
