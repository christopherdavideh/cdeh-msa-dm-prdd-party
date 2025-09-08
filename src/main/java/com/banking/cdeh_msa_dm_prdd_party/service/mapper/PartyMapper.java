package com.banking.cdeh_msa_dm_prdd_party.service.mapper;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.PartyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    PartyDto toPartyDTO(Party party);
    Party toParty(PartyDto partyDTO);
}
