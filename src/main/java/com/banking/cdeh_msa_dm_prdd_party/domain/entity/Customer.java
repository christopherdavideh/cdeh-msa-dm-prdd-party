package com.banking.cdeh_msa_dm_prdd_party.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("customer")
public class Customer {
    @Id
    @Column("customer_id")
    private UUID customerId;
    private String password;
    private Boolean status;
    @Column("party_id")
    private UUID partyId;

}
