package com.banking.cdeh_msa_dm_prdd_party.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    private CustomerDto customer;
    private PartyDto party;
}
