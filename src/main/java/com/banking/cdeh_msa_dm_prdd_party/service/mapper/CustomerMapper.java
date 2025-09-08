package com.banking.cdeh_msa_dm_prdd_party.service.mapper;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Customer;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toCustomerDTO(Customer customer);
    Customer toCustomer(CustomerDto customerDto);
}

