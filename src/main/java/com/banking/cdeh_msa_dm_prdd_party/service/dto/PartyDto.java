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
public class PartyDto {
    private UUID partyId;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}

