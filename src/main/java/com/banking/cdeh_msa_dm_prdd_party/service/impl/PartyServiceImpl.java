package com.banking.cdeh_msa_dm_prdd_party.service.impl;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.repository.PartyRepository;
import com.banking.cdeh_msa_dm_prdd_party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;

    @Override
    public Mono<Party> createParty(Party party) {
        return partyRepository.save(party)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating Party: " + e.getMessage())));
    }

    @Override
    public Mono<Party> getPartyById(UUID partyId) {
        return partyRepository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with id: " + partyId)))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error retrieving Party: " + e.getMessage())));
    }

    @Override
    public Flux<Party> getAllParties() {
        return partyRepository.findAll()
                .onErrorResume(e -> Flux.error(new RuntimeException("Error retrieving Parties: " + e.getMessage())));
    }

    @Override
    public Mono<Party> updateParty(UUID partyId, Party party) {
        return partyRepository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with id: " + partyId)))
                .flatMap(existing -> {
                    party.setPartyId(partyId);
                    return partyRepository.save(party);
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating Party: " + e.getMessage())));
    }

    @Override
    public Mono<Void> deleteParty(UUID partyId) {
        return partyRepository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with id: " + partyId)))
                .flatMap(existing -> partyRepository.deleteById(partyId))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting Party: " + e.getMessage())));

    }

}