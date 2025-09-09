package com.banking.cdeh_msa_dm_prdd_party.service.mapper;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.PartyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toCustomerDTO(Customer customer);

    Customer toCustomer(CustomerDto customerDto);

    default CustomerResponseDto toCustomerResponseDto(Party party, Customer customer, PartyMapper partyMapper) {
        return CustomerResponseDto.builder()
                .customerId(customer.getCustomerId())
                .party(partyMapper.toPartyDTO(party))
                .password(customer.getPassword())
                .status(customer.getStatus())
                .build();
    }
}