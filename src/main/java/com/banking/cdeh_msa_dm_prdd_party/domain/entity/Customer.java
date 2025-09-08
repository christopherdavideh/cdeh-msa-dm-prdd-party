package com.banking.cdeh_msa_dm_prdd_party.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @Column("customer_id")
    UUID customerId;
    String password;
    Boolean status;
    @Column("party_id")
    UUID partyId;
}
