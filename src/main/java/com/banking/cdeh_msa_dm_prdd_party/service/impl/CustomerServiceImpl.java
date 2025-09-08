package com.banking.cdeh_msa_dm_prdd_party.service.impl;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.repository.CustomerRepository;
import com.banking.cdeh_msa_dm_prdd_party.service.CustomerService;
import com.banking.cdeh_msa_dm_prdd_party.service.PartyService;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import com.banking.cdeh_msa_dm_prdd_party.service.mapper.CustomerMapper;
import com.banking.cdeh_msa_dm_prdd_party.service.mapper.PartyMapper;
import com.banking.cdeh_msa_dm_prdd_party.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final PartyService partyService;
    private final CustomerMapper customerMapper;
    private final PartyMapper partyMapper;

    @Override
    public Mono<CustomerResponseDto> createCustomerWithParty(CustomerRequestDto customerRequestDto) {
        Party partyEntity = partyMapper.toParty(customerRequestDto.getParty());
        return partyService.createParty(partyEntity)
            .doFirst(() -> log.info(LogMessages.CREATE_CUSTOMER_REQUEST, customerRequestDto))
            .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_CREATING_PARTY + error.getMessage())))
            .flatMap(savedParty -> {
                Customer customerEntity = Customer.builder().
                        partyId(savedParty.getPartyId())
                        .password(customerRequestDto.getCustomer().getPassword())
                        .status(customerRequestDto.getCustomer().getStatus())
                        .build();
                return customerRepository.save(customerEntity)
                    .map(savedCustomer -> CustomerResponseDto.builder()
                        .customerId(savedCustomer.getCustomerId())
                        .password(savedCustomer.getPassword())
                        .status(savedCustomer.getStatus())
                        .party(partyMapper.toPartyDTO(savedParty))
                        .build())
                    .doOnSuccess(dto -> log.info(LogMessages.CREATE_CUSTOMER_SUCCESS, dto))
                    .doOnError(error -> log.error(LogMessages.CREATE_CUSTOMER_ERROR, error.getMessage()))
                    .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_CREATING_CUSTOMER + error.getMessage())));
            })
            .doOnError(error -> log.error(LogMessages.CREATE_PARTY_ERROR, error.getMessage()));
    }

    @Override
    public Flux<CustomerResponseDto> getAllCustomerDTOs() {
        return customerRepository.findAll()
            .doFirst(() -> log.info(LogMessages.GET_ALL_CUSTOMERS_REQUEST))
            .flatMap(customerEntity -> partyService.getPartyById(customerEntity.getPartyId())
                .map(partyEntity -> CustomerResponseDto.builder()
                    .customerId(customerEntity.getCustomerId())
                    .password(customerEntity.getPassword())
                    .status(customerEntity.getStatus())
                    .party(partyMapper.toPartyDTO(partyEntity))
                    .build())
                .doOnSuccess(dto -> log.info(LogMessages.GET_ALL_CUSTOMERS_SUCCESS, dto))
                .doOnError(error -> log.error(LogMessages.GET_PARTY_FOR_CUSTOMER_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_PARTY_FOR_CUSTOMER + error.getMessage()))))
            .doOnError(error -> log.error(LogMessages.GET_ALL_CUSTOMERS_ERROR, error.getMessage()))
            .onErrorResume(error -> Flux.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_CUSTOMERS + error.getMessage())));
    }

    @Override
    public Mono<CustomerResponseDto> getCustomerDTOById(UUID customerId) {
        return customerRepository.findById(customerId)
            .doFirst(() -> log.info(LogMessages.GET_CUSTOMER_BY_ID_REQUEST, customerId))
            .switchIfEmpty(Mono.error(new RuntimeException(LogMessages.CUSTOMER_NOT_FOUND + customerId)))
            .flatMap(customerEntity -> partyService.getPartyById(customerEntity.getPartyId())
                .map(partyEntity -> CustomerResponseDto.builder()
                    .customerId(customerEntity.getCustomerId())
                    .password(customerEntity.getPassword())
                    .status(customerEntity.getStatus())
                    .party(partyMapper.toPartyDTO(partyEntity))
                    .build())
                .doOnSuccess(dto -> log.info(LogMessages.GET_CUSTOMER_BY_ID_SUCCESS, dto))
                .doOnError(error -> log.error(LogMessages.GET_PARTY_BY_ID_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_PARTY_FOR_CUSTOMER + error.getMessage()))))
            .doOnError(error -> log.error(LogMessages.GET_CUSTOMER_BY_ID_ERROR, error.getMessage()))
            .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_CUSTOMER + error.getMessage())));
    }

    @Override
    public Mono<CustomerResponseDto> updateCustomerWithParty(UUID customerId, CustomerRequestDto customerRequestDto) {
        return customerRepository.findById(customerId)
            .doFirst(() -> log.info(LogMessages.UPDATE_CUSTOMER_REQUEST, customerId, customerRequestDto))
            .switchIfEmpty(Mono.error(new RuntimeException(LogMessages.CUSTOMER_NOT_FOUND + customerId)))
            .flatMap(existingCustomer -> partyService.getPartyById(existingCustomer.getPartyId())
                .switchIfEmpty(Mono.error(new RuntimeException(LogMessages.PARTY_NOT_FOUND_FOR_CUSTOMER + customerId)))
                .flatMap(existingParty -> {
                    Party updatedParty = partyMapper.toParty(customerRequestDto.getParty());
                    updatedParty.setPartyId(existingParty.getPartyId());
                    return partyService.updateParty(existingParty.getPartyId(), updatedParty)
                        .doOnSuccess(p -> log.info(LogMessages.UPDATE_PARTY_SUCCESS, p))
                        .doOnError(error -> log.error(LogMessages.UPDATE_PARTY_ERROR, error.getMessage()))
                        .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_UPDATING_PARTY + error.getMessage())))
                        .flatMap(savedParty -> {
                            Customer updatedCustomer = Customer.builder().
                                    customerId(customerId).
                                    password(customerRequestDto.getCustomer().getPassword()).
                                    status(customerRequestDto.getCustomer().getStatus()).
                                    partyId(savedParty.getPartyId()).
                                    build();
                            return customerRepository.save(updatedCustomer)
                                .map(savedCustomer -> CustomerResponseDto.builder()
                                        .customerId(savedCustomer.getCustomerId())
                                        .password(savedCustomer.getPassword())
                                        .status(savedCustomer.getStatus())
                                        .party(partyMapper.toPartyDTO(savedParty))
                                        .build())
                                .doOnSuccess(dto -> log.info(LogMessages.UPDATE_CUSTOMER_SUCCESS, dto))
                                .doOnError(error -> log.error(LogMessages.UPDATE_CUSTOMER_ERROR, error.getMessage()))
                                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_UPDATING_CUSTOMER + error.getMessage())));
                        });
                }))
            .doOnError(error -> log.error(LogMessages.UPDATE_CUSTOMER_ERROR, error.getMessage()))
            .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_UPDATING_CUSTOMER + error.getMessage())));
    }

    @Override
    public Mono<Void> deleteCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
            .doFirst(() -> log.info(LogMessages.DELETE_CUSTOMER_REQUEST, customerId))
            .switchIfEmpty(Mono.error(new RuntimeException(LogMessages.CUSTOMER_NOT_FOUND + customerId)))
            .flatMap(customerEntity -> customerRepository.deleteById(customerId)
                .doOnSuccess(v -> log.info(LogMessages.DELETE_CUSTOMER_SUCCESS, customerId))
                .doOnError(error -> log.error(LogMessages.DELETE_CUSTOMER_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_DELETING_CUSTOMER + error.getMessage())))
                .then(partyService.deleteParty(customerEntity.getPartyId())
                    .doOnSuccess(v -> log.info(LogMessages.DELETE_PARTY_SUCCESS, customerEntity.getPartyId()))
                    .doOnError(error -> log.error(LogMessages.DELETE_PARTY_ERROR, error.getMessage()))
                    .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_DELETING_PARTY + error.getMessage())))))
            .doOnError(error -> log.error(LogMessages.DELETE_CUSTOMER_ERROR, error.getMessage()))
            .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_DELETING_CUSTOMER + error.getMessage())));
    }
}
