package com.banking.cdeh_msa_dm_prdd_party.domain.entity;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Party {
    @Id
    @Column("party_id")
    UUID partyId;
    String name;
    String gender;
    Integer age;
    String identification;
    String address;
    String phone;
}
