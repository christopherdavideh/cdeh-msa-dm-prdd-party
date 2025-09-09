package com.banking.cdeh_msa_dm_prdd_party.service.impl;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.repository.CustomerRepository;
import com.banking.cdeh_msa_dm_prdd_party.repository.PartyRepository;
import com.banking.cdeh_msa_dm_prdd_party.service.CustomerService;
import com.banking.cdeh_msa_dm_prdd_party.service.PartyService;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import com.banking.cdeh_msa_dm_prdd_party.service.mapper.CustomerMapper;
import com.banking.cdeh_msa_dm_prdd_party.service.mapper.PartyMapper;
import com.banking.cdeh_msa_dm_prdd_party.util.LogMessages;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    CustomerRepository customerRepository;
    PartyService partyService;
    CustomerMapper customerMapper;
    PartyMapper partyMapper;
    private final PartyRepository partyRepository;


    @Override
    public Mono<CustomerResponseDto> createCustomerWithParty(CustomerRequestDto customerRequestDto) {
        Party partyEntity = partyMapper.toParty(customerRequestDto.getParty());
        Customer customerEntity = customerMapper.toCustomer(customerRequestDto.getCustomer());
        return partyService.createParty(partyEntity)
                .doFirst(() -> log.info(LogMessages.CREATE_CUSTOMER_REQUEST, customerRequestDto))
                .doOnSuccess(savedParty -> log.info(LogMessages.PARTY_SAVED_SUCCESS, savedParty))
                .doOnError(error -> log.error(LogMessages.CREATE_PARTY_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_CREATING_PARTY + error.getMessage())))
                .flatMap(savedParty -> {
                    customerEntity.setPartyId(savedParty.getPartyId());
                    return customerRepository.save(customerEntity)
                            .doOnSuccess(savedCustomer -> log.info(LogMessages.CUSTOMER_SAVED_SUCCESS, savedCustomer))
                            .doOnError(error -> log.error(LogMessages.CREATE_CUSTOMER_ERROR, error.getMessage()))
                            .map(savedCustomer -> customerMapper.toCustomerResponseDto(savedParty, savedCustomer, partyMapper))
                            .doOnSuccess(dto -> log.info(LogMessages.CREATE_CUSTOMER_SUCCESS, dto))
                            .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_CREATING_CUSTOMER + error.getMessage())));
                });
    }

    @Override
    public Flux<CustomerResponseDto> getAllCustomerDTOs() {
        return customerRepository.findAllActiveCustomers()
                .doFirst(() -> log.info(LogMessages.GET_ALL_CUSTOMERS_REQUEST))
                .doOnNext(customer -> log.info(LogMessages.CUSTOMER_FOUND, customer))
                .flatMap(customer -> partyService.getPartyById(customer.getPartyId())
                        .doOnSuccess(party -> log.info(LogMessages.PARTY_FOUND_FOR_CUSTOMER, party))
                        .doOnError(error -> log.error(LogMessages.GET_PARTY_FOR_CUSTOMER_ERROR, error.getMessage()))
                        .map(party -> customerMapper.toCustomerResponseDto(party, customer, partyMapper))
                        .doOnSuccess(dto -> log.info(LogMessages.GET_ALL_CUSTOMERS_SUCCESS, dto))
                        .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_PARTY_FOR_CUSTOMER + error.getMessage()))))
                .doOnError(error -> log.error(LogMessages.GET_ALL_CUSTOMERS_ERROR, error.getMessage()))
                .onErrorResume(error -> Flux.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_CUSTOMERS + error.getMessage())));
    }

    @Override
    public Mono<CustomerResponseDto> getCustomerDTOById(UUID customerId) {
        return customerRepository.findActiveCustomerById(customerId)
                .doFirst(() -> log.info(LogMessages.GET_CUSTOMER_BY_ID_REQUEST, customerId))
                .doOnNext(customer -> log.info(LogMessages.CUSTOMER_BY_ID_FOUND, customer))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(LogMessages.CUSTOMER_BY_ID_NOT_FOUND, customerId);
                    return Mono.error(new RuntimeException(LogMessages.CUSTOMER_NOT_FOUND + customerId));
                }))
                .flatMap(customer ->
                        partyService.getPartyById(customer.getPartyId())
                                .doOnSuccess(party -> log.info(LogMessages.PARTY_BY_ID_FOUND, party))
                                .doOnError(error -> log.error(LogMessages.GET_PARTY_BY_ID_ERROR, error.getMessage()))
                                .map(party -> customerMapper.toCustomerResponseDto(party, customer, partyMapper))
                                .doOnSuccess(dto -> log.info(LogMessages.GET_CUSTOMER_BY_ID_SUCCESS, dto))
                                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_PARTY_FOR_CUSTOMER + error.getMessage()))))
                .doOnError(error -> log.error(LogMessages.GET_CUSTOMER_BY_ID_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_RETRIEVING_CUSTOMER + error.getMessage())));
    }

    @Override
    public Mono<CustomerResponseDto> updateCustomerWithParty(UUID customerId, CustomerRequestDto customerRequestDto) {
        return customerRepository.findById(customerId)
                .doFirst(() -> log.info(LogMessages.UPDATE_CUSTOMER_REQUEST, customerId, customerRequestDto))
                .doOnNext(customer -> log.info(LogMessages.CUSTOMER_UPDATE_FOUND, customer))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(LogMessages.CUSTOMER_UPDATE_NOT_FOUND, customerId);
                    return Mono.error(new RuntimeException(LogMessages.CUSTOMER_NOT_FOUND + customerId));
                }))
                .flatMap(existingCustomer -> {
                    UUID partyId = existingCustomer.getPartyId();
                    return partyService.getPartyById(partyId)
                            .doOnNext(party -> log.info(LogMessages.PARTY_UPDATE_FOUND, party))
                            .doOnError(error -> log.error(LogMessages.PARTY_UPDATE_ERROR, error.getMessage()))
                            .flatMap(existingParty -> {
                                Party updatedParty = partyMapper.toParty(customerRequestDto.getParty());
                                updatedParty.setPartyId(partyId);

                                return partyService.updateParty(existingParty.getPartyId(), updatedParty)
                                        .doOnNext(savedParty -> log.info(LogMessages.UPDATE_PARTY_SUCCESS, savedParty))
                                        .doOnError(error -> log.error(LogMessages.UPDATE_PARTY_ERROR, error.getMessage()))
                                        .flatMap(savedParty -> {
                                            Customer updatedCustomer = new Customer();
                                            updatedCustomer.setCustomerId(customerId);
                                            updatedCustomer.setPassword(customerRequestDto.getCustomer().getPassword());
                                            updatedCustomer.setStatus(customerRequestDto.getCustomer().getStatus());
                                            updatedCustomer.setPartyId(savedParty.getPartyId());

                                            return customerRepository.save(updatedCustomer)
                                                    .doOnNext(savedCustomer -> log.info(LogMessages.CUSTOMER_UPDATED_SUCCESS, savedCustomer))
                                                    .doOnError(error -> log.error(LogMessages.UPDATE_CUSTOMER_ERROR, error.getMessage()))
                                                    .map(savedCustomer -> {
                                                        CustomerResponseDto responseDto = CustomerResponseDto.builder()
                                                                .customerId(savedCustomer.getCustomerId())
                                                                .password(savedCustomer.getPassword())
                                                                .status(savedCustomer.getStatus())
                                                                .party(partyMapper.toPartyDTO(savedParty))
                                                                .build();
                                                        return responseDto;
                                                    })
                                                    .doOnSuccess(dto -> log.info(LogMessages.UPDATE_CUSTOMER_SUCCESS, dto));
                                        });
                            });
                })
                .doOnError(error -> log.error(LogMessages.UPDATE_CUSTOMER_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_UPDATING_CUSTOMER + error.getMessage())));
    }

    @Override
    public Mono<Void> deleteCustomer(UUID customerId) {
        return customerRepository.deactivateCustomerById(customerId)
                .doFirst(() -> log.info(LogMessages.DELETE_CUSTOMER_REQUEST, customerId))
                .doOnSuccess(result -> log.info(LogMessages.DELETE_CUSTOMER_SUCCESS, customerId))
                .doOnError(error -> log.error(LogMessages.DELETE_CUSTOMER_ERROR, error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException(LogMessages.ERROR_DELETING_CUSTOMER + error.getMessage())))
                .then();
    }
}
