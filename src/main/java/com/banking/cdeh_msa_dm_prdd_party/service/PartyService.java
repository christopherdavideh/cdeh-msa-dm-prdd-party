package com.banking.cdeh_msa_dm_prdd_party.service;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface PartyService {
    Mono<Party> createParty(Party party);
    Mono<Party> getPartyById(UUID partyId);
    Flux<Party> getAllParties();
    Mono<Party> updateParty(UUID partyId, Party party);
    Mono<Void> deleteParty(UUID partyId);
}

