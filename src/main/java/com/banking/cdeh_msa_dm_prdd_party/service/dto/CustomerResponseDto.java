package com.banking.cdeh_msa_dm_prdd_party.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private UUID customerId;
    private String password;
    private Boolean status;
    private PartyDto party;
}
